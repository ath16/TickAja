package com.tickaja.model;

public class Film {
    private int id;
    private String judul;
    private String deskripsi;
    private int durasi; 
    private String posterUrl;

    public Film() {}

    public Film(int id, String judul, String deskripsi, int durasi, String posterUrl) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.durasi = durasi;
        this.posterUrl = posterUrl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public int getDurasi() { return durasi; }
    public void setDurasi(int durasi) { this.durasi = durasi; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    
    @Override
    public String toString() {
        return judul; 
    }
}