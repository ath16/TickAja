package pbo.kelompok4.model;

public class DetailReservasi {
    private int id;
    private Reservasi reservasi; // Transaksi Induk
    private Kursi kursi;         // Kursi yang dipesan

    public DetailReservasi() {
    }

    public DetailReservasi(int id, Reservasi reservasi, Kursi kursi) {
        this.id = id;
        this.reservasi = reservasi;
        this.kursi = kursi;
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
}