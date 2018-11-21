package com.uwindsor.notekeeper.drive;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.CharStreams;
import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;
import com.uwindsor.notekeeper.util.Constants;
import com.uwindsor.notekeeper.util.SimpleEncryptDecrypt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GooglePersistenceService implements PersistenceService {
    private Drive service;

    public GooglePersistenceService() {
        this.service = GoogleDriveService.getInstance();
    }

    @Override
    public void setup() throws IOException {
        // Setup root directory
        File f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR);
        Constants.NOTE_KEEPER_DIR_ID = f.getId();
        // Setup notes directory
        f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR_ID, Constants.NOTES_DIR);
        Constants.NOTES_DIR_ID = f.getId();
        // Setup encrypted directory
        f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR_ID, Constants.ENCRYPTED_NOTES_DIR);
        Constants.ENCRYPTED_NOTE_DIR_ID = f.getId();
    }

    @Override
    public List<Note> getAllNotes() throws IOException {
        List<Note> notes = getNotes();
        notes.addAll(getEncryptedNotes());
        return notes;
    }

    @Override
    public List<Note> getNotes() throws IOException {
        FileList list = GoogleDriveClient.getFiles(service, Constants.NOTES_DIR_ID);
        return list.getFiles()
                .stream()
                .map(driveFile -> Mapper.toNote(driveFile, false))
                .collect(Collectors.toList());
    }

    @Override
    public List<Note> getEncryptedNotes() throws IOException {
        FileList list = GoogleDriveClient.getFiles(service, Constants.ENCRYPTED_NOTE_DIR_ID);
        return list.getFiles()
                .stream()
                .map(driveFile -> Mapper.toNote(driveFile, true))
                .collect(Collectors.toList());
    }

    @Override
    public Note encryptNote(Note note, String content, String password) throws IOException {
        String encryptedContent = SimpleEncryptDecrypt.encrypt(content, password);
        File fileMetadata = new File();
        fileMetadata.setParents(Collections.singletonList(Constants.ENCRYPTED_NOTE_DIR_ID));
        fileMetadata.setName(note.getFileName());
        fileMetadata.setMimeType("application/octet-stream");
        File file = service.files().create(fileMetadata, new InputStreamContent("application/octet-stream",
                new ByteArrayInputStream(encryptedContent.getBytes())))
                .setFields("id, name, modifiedTime")
                .execute();
        //Delete un-encrypted file
        service.files().delete(note.getId()).execute();
        return Mapper.toNote(file, false);
    }

    @Override
    public String decryptNote(Note note, String password) throws IOException {
        return SimpleEncryptDecrypt.decrypt(GoogleDriveClient.getFile(service, note.getId()), password);
    }

    @Override
    public String getNoteContent(Note note) throws IOException {
        return GoogleDriveClient.getFile(service, note.getId());
    }

    @Override
    public void saveNoteContent(Note note, String content) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(note.getFileName());
        fileMetadata.setMimeType("application/octet-stream");
        service.files()
                .update(note.getId(), fileMetadata,
                        new InputStreamContent("application/octet-stream",
                                new ByteArrayInputStream(content.getBytes())))
                .setFields("id").execute();
    }

    @Override
    public Note createNote(String fileName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setParents(Collections.singletonList(Constants.NOTES_DIR_ID));
        fileMetadata.setName(fileName);
        fileMetadata.setMimeType("application/octet-stream");
        File file = service.files().create(fileMetadata)
                .setFields("id, name, modifiedTime")
                .execute();
        return Mapper.toNote(file, false);
    }

    @Override
    public void deleteNote(Note note) throws IOException {
        String fileId;
        fileId = note.getId();
        service.files().delete(fileId).execute();
    }

    @Override
    public String savePassword(String password) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setParents(Collections.singletonList("appDataFolder"));
        fileMetadata.setName("password");
        String sha256hex = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        service.files().create(fileMetadata,
                new InputStreamContent("application/octet-stream",
                        new ByteArrayInputStream(sha256hex.getBytes())) ).execute();
        return sha256hex;
    }

    @Override
    public String getPassword() throws IOException {
        String fileId;
        FileList files = service.files().list()
                .setSpaces("appDataFolder")
                .setFields("nextPageToken, files(id, name)")
                .setPageSize(10)
                .execute();
        if(files.getFiles().size() == 0){
            return null;
        }
        else{
            fileId = files.getFiles().get(0).getId();
        }
        InputStream inputStream = service.files()
                .get(fileId)
                .executeMediaAsInputStream();

        return CharStreams.toString(new InputStreamReader(
                inputStream, Charsets.UTF_8));
    }
}
