package pbo.kelompok4.model;

public class Kursi {
    private int id;
    private int studioId; // Foreign Key ke Studio
    private String nomorBaris; // Contoh: "A", "B"
    private int nomorKursi;    // Contoh: 1, 2, 3

    // Constructor Kosong
    public Kursi() {
    }

    // Constructor Lengkap
    public Kursi(int id, int studioId, String nomorBaris, int nomorKursi) {
        this.id = id;
        this.studioId = studioId;
        this.nomorBaris = nomorBaris;
        this.nomorKursi = nomorKursi;
    }

    // Helper Method: Menggabungkan Baris dan Nomor (Misal: "A1")
    public String getKodeKursi() {
        return nomorBaris + nomorKursi;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public String getNomorBaris() {
        return nomorBaris;
    }

    public void setNomorBaris(String nomorBaris) {
        this.nomorBaris = nomorBaris;
    }

    public int getNomorKursi() {
        return nomorKursi;
    }

    public void setNomorKursi(int nomorKursi) {
        this.nomorKursi = nomorKursi;
    }
}