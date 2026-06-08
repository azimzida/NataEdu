package com.nataedu;

import com.google.firebase.Timestamp;

public class History {
    private String title;
    private String author;
    private Timestamp timestamp;

    // Konstruktor kosong wajib ada untuk Firestore
    public History() {}

    public History(String title, String author, Timestamp timestamp) {
        this.title = title;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}