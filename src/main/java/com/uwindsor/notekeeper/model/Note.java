package com.uwindsor.notekeeper.model;

public class Note {
    private String id;
    private String fileName;
    private Boolean isEncrypted;

    public Note(String id, String fileName, Boolean isEncrypted) {
        this.id = id;
        this.fileName = fileName;
        this.isEncrypted = isEncrypted;
    }

    public Note() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        isEncrypted = encrypted;
    }
}
