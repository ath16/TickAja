package pbo.kelompok4.model;

public class Film {
    // Kita gunakan nama variabel yang deskriptif sesuai versi HEAD
    // agar cocok dengan database (film_id, durasi_menit)
    private int filmId;
    private String judul;
    private String deskripsi;
    private int durasiMenit; 
    private String posterUrl;

    // 1. Constructor Kosong (Penting untuk fleksibilitas)
    public Film() {
    }

    // 2. Constructor Lengkap
    public Film(int filmId, String judul, String deskripsi, int durasiMenit, String posterUrl) {
        this.filmId = filmId;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.durasiMenit = durasiMenit;
        this.posterUrl = posterUrl;
    }

    // 3. Getter & Setter (Gabungan keduanya agar lengkap)
    
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public void setDurasiMenit(int durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return judul; // Agar saat masuk ComboBox/ListView yang muncul Judulnya
    }
}