package pbo.kelompok4.model;

import java.sql.Timestamp;

public class JadwalTayang {
    private int id;
    private Film film;      // Relasi ke Objek Film
    private Studio studio;  // Relasi ke Objek Studio
    private Timestamp waktuTayang;
    private int hargaTiket;

    public JadwalTayang() {
    }

    public JadwalTayang(int id, Film film, Studio studio, Timestamp waktuTayang, int hargaTiket) {
        this.id = id;
        this.film = film;
        this.studio = studio;
        this.waktuTayang = waktuTayang;
        this.hargaTiket = hargaTiket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Timestamp getWaktuTayang() {
        return waktuTayang;
    }

    public void setWaktuTayang(Timestamp waktuTayang) {
        this.waktuTayang = waktuTayang;
    }

    public int getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(int hargaTiket) {
        this.hargaTiket = hargaTiket;
    }
    
    @Override
    public String toString() {
        // Method ini penting agar saat jadwal dimasukkan ke ComboBox, 
        // yang muncul adalah teks yang mudah dibaca user
        // Contoh output: "2025-01-01 14:00:00 (Rp 50000)"
        return waktuTayang + " (Rp " + hargaTiket + ")";
    }
}