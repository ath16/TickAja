package pbo.kelompok4.model;

public class DetailReservasi {
    private int id;
    private Reservasi reservasi; // Transaksi Induk
    private Kursi kursi; // Kursi yang dipesan
    private int hargaKursi;

    public DetailReservasi() {
    }

    public DetailReservasi(int id, Reservasi reservasi, Kursi kursi, int hargaKursi) {
        this.id = id;
        this.reservasi = reservasi;
        this.kursi = kursi;
        this.hargaKursi = hargaKursi;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservasi getReservasi() {
        return reservasi;
    }

    public void setReservasi(Reservasi reservasi) {
        this.reservasi = reservasi;
    }

    public Kursi getKursi() {
        return kursi;
    }

    public void setKursi(Kursi kursi) {
        this.kursi = kursi;
    }

    public int getHargaKursi() {
        return hargaKursi;
    }

    public void setHargaKursi(int hargaKursi) {
        this.hargaKursi = hargaKursi;
    }
}