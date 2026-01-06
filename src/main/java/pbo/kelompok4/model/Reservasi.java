package pbo.kelompok4.model;

import java.sql.Timestamp;

public class Reservasi {
    private int id;
    private User pengguna;          // Relasi ke User
    private JadwalTayang jadwal;    // Relasi ke JadwalTayang
    private int totalHarga;
    private String statusPembayaran; // "pending", "sukses", "batal"
    private Timestamp waktuReservasi;

    public Reservasi() {
    }

    public Reservasi(int id, User pengguna, JadwalTayang jadwal, int totalHarga, String statusPembayaran, Timestamp waktuReservasi) {
        this.id = id;
        this.pengguna = pengguna;
        this.jadwal = jadwal;
        this.totalHarga = totalHarga;
        this.statusPembayaran = statusPembayaran;
        this.waktuReservasi = waktuReservasi;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPengguna() {
        return pengguna;
    }

    public void setPengguna(User pengguna) {
        this.pengguna = pengguna;
    }

    public JadwalTayang getJadwal() {
        return jadwal;
    }

    public void setJadwal(JadwalTayang jadwal) {
        this.jadwal = jadwal;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public Timestamp getWaktuReservasi() {
        return waktuReservasi;
    }

    public void setWaktuReservasi(Timestamp waktuReservasi) {
        this.waktuReservasi = waktuReservasi;
    }
}