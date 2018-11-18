package com.uwindsor.notekeeper.dropbox;

import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;

import java.io.IOException;
import java.util.List;

public class DropBoxPersistenceService implements PersistenceService {

    @Override
    public void setup() throws IOException {

    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public List<Note> getNotes() {
        return null;
    }

    @Override
    public List<Note> getEncryptedNotes() {
        return null;
    }

    @Override
    public void encryptNote(Note note, String content, String password) {

    }

    @Override
    public String decryptNote(Note note, String password) {
        return null;
    }

    @Override
    public String getNoteContent(Note note) {
        return null;
    }

    @Override
    public void saveNoteContent(Note note, String content) throws IOException {

    }

    @Override
    public Note createNote(String fileName) throws IOException {
        return null;
    }
}
