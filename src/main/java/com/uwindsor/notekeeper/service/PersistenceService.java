package com.uwindsor.notekeeper.service;

import com.uwindsor.notekeeper.model.Note;

import java.io.IOException;
import java.util.List;

public interface PersistenceService {

    void setup() throws IOException;

    List<Note> getAllNotes() throws IOException;

    List<Note> getNotes() throws IOException;

    List<Note> getEncryptedNotes() throws IOException;

    Note encryptNote(Note note, String content, String password) throws IOException;

    String decryptNote(Note note, String password) throws IOException;

    String getNoteContent(Note note) throws IOException;

    void saveNoteContent(Note note, String content) throws IOException;

    Note createNote(String fileName) throws IOException;

    void deleteNote(Note note) throws IOException;

    String savePassword(String password) throws IOException;

    String getPassword() throws IOException;
}
