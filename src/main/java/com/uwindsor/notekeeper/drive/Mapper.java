package com.uwindsor.notekeeper.drive;

import com.google.api.client.http.FileContent;
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

    static File fromNote(Note note, String content){
        File fileMetadata = new File();
        fileMetadata.setName("photo.jpg");
        java.io.File filePath = new java.io.File("files/photo.jpg");
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        return null;
    }
}
