package com.uwindsor.notekeeper.drive;

import com.google.api.services.drive.model.File;
import com.uwindsor.notekeeper.model.Note;

import java.util.Date;

class Mapper {

    static Note toNote(File driveFile, boolean isEncrypted) {
        Note note = new Note();
        note.setId(driveFile.getId());
        note.setFileName(driveFile.getName());
        note.setCreationDate(new Date(driveFile.getModifiedTime().getValue()));
        note.setEncrypted(isEncrypted);
        return note;
    }
}
