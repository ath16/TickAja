package pbo.kelompok4.model;

public class Film {
    private int filmId;
    private String judul;
    private String deskripsi;
    private int durasiMenit;
    private String posterUrl;

    public Film(int filmId, String judul, String deskripsi, int durasiMenit, String posterUrl) {
        this.filmId = filmId;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.durasiMenit = durasiMenit;
        this.posterUrl = posterUrl;
    }

    // Getters
    public int getFilmId() { return filmId; }
    public String getJudul() { return judul; }
    public String getDeskripsi() { return deskripsi; }
    public int getDurasiMenit() { return durasiMenit; }
    public String getPosterUrl() { return posterUrl; }

    @Override
    public String toString() {
        return judul; // Agar saat masuk ListView yang muncul Judulnya
    }
}