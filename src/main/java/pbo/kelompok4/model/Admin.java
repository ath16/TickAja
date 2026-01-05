package pbo.kelompok4.model;

public class Admin extends Pengguna {

    public Admin() {
        super();
        this.setRole("admin");
    }

    public Admin(int id, String username, String password, String namaLengkap, String email) {
        super(id, username, password, namaLengkap, email, "admin");
    }

    // Implementasi Polimorfisme
    @Override
    public void tampilkanMenu() {
        System.out.println("Menampilkan Dashboard Khusus Admin (CRUD Film, Cek Transaksi)");
    }
}