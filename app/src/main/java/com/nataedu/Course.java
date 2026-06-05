package com.nataedu;

import com.google.firebase.Timestamp;

public class Course {
    private String id;
    private String nama_course;
    private String deskripsi;
    private String kategori;
    private String author;
    private String price;
    private String pdf_url;
    private Timestamp created_at;
    private Timestamp publish_at;
    private String image_res; 

    public Course() {
        // Required for Firestore
    }

    public Course(String id, String nama_course, String deskripsi, String kategori, String author) {
        this.id = id;
        this.nama_course = nama_course;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.author = author;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNama_course() { return nama_course; }
    public void setNama_course(String nama_course) { this.nama_course = nama_course; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getPdf_url() { return pdf_url; }
    public void setPdf_url(String pdf_url) { this.pdf_url = pdf_url; }

    public Timestamp getCreated_at() { return created_at; }
    public void setCreated_at(Timestamp created_at) { this.created_at = created_at; }

    public Timestamp getPublish_at() { return publish_at; }
    public void setPublish_at(Timestamp publish_at) { this.publish_at = publish_at; }

    public String getImage_res() { return image_res; }
    public void setImage_res(String image_res) { this.image_res = image_res; }
}