package com.uwindsor.notekeeper.repository;

import com.google.api.services.drive.Drive;
import com.uwindsor.notekeeper.model.Note;

import java.util.List;

public class DriveNoteRepository implements CrudRepository<Note> {

    private Drive driveService;

    public DriveNoteRepository(Drive driveService) {
        this.driveService = driveService;
    }

    @Override
    public Note save(Note item, String content) {
        return null;
    }

    @Override
    public Note findById(Note item) {
        return null;
    }

    @Override
    public List<Note> findAllInPath(String path) {
        return null;
    }

    @Override
    public Note deleteById(String id) {
        return null;
    }
}
