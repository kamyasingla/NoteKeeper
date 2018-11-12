package com.uwindsor.notekeeper.drive;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;
import com.uwindsor.notekeeper.util.Constants;

import java.io.IOException;
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
}