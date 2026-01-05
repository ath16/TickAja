package pbo.kelompok4.model;

public class User extends Pengguna {
    
    private String noTelepon;

    public User() {
        super();
        this.setRole("user"); // Default role
    }

    public User(int id, String username, String password, String namaLengkap, String email, String noTelepon) {
        // Memanggil constructor induk (Pengguna)
        super(id, username, password, namaLengkap, email, "user");
        this.noTelepon = noTelepon;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    // Implementasi Polimorfisme
    @Override
    public void tampilkanMenu() {
        System.out.println("Menampilkan Dashboard Khusus User (Film Gallery, Order Saya)");
    }
}