package com.uwindsor.notekeeper.drive;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;
import com.uwindsor.notekeeper.util.Constants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    public void encryptNote(Note note, String content, String password) {
    }

    @Override
    public String decryptNote(Note note, String password) {
        return null;
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
    public void deleteNote(Note note) {
        String fileId;
        fileId = note.getId();
        try {
            service.files().delete(fileId).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

}
