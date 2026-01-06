package com.tickaja.model;

import java.time.LocalDateTime;

public class JadwalTayang {
    private int id;
    private Film film;       
    private String namaStudio; 
    private int studioId;      
    private LocalDateTime waktuTayang;
    private int harga;

    public JadwalTayang(int id, Film film, int studioId, String namaStudio, LocalDateTime waktuTayang, int harga) {
        this.id = id;
        this.film = film;
        this.studioId = studioId;
        this.namaStudio = namaStudio;
        this.waktuTayang = waktuTayang;
        this.harga = harga;
    }

    public int getId() { return id; }
    public Film getFilm() { return film; }
    public String getNamaStudio() { return namaStudio; }
    public int getStudioId() { return studioId; }
    public LocalDateTime getWaktuTayang() { return waktuTayang; }
    public int getHarga() { return harga; }
    
    // Helper untuk menampilkan Judul Film di Tabel
    public String getJudulFilm() { return film.getJudul(); }
}