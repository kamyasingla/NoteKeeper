package com.uwindsor.notekeeper.model;

import java.util.Date;

public class Note {
    private String id;
    private String fileName;
    private Boolean isEncrypted;
    private Date creationDate;

    public Note(String id, String fileName, Date creationDate, Boolean isEncrypted) {
        this.id = id;
        this.fileName = fileName;
        this.isEncrypted = isEncrypted;
        this.creationDate = creationDate;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
