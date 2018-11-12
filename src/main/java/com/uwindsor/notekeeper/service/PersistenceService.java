package com.uwindsor.notekeeper.service;

import com.uwindsor.notekeeper.model.Note;

import java.io.IOException;
import java.util.List;

public interface PersistenceService {

    void setup() throws IOException;

    List<Note> getAllNotes() throws IOException;

    List<Note> getNotes() throws IOException;

    List<Note> getEncryptedNotes() throws IOException;

    void encryptNote(Note note, String content, String password) throws IOException;

    String decryptNote(Note note, String password) throws IOException;

    String getNoteContent(Note note) throws IOException;
}
