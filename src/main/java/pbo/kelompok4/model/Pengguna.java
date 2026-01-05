package pbo.kelompok4.model;

public abstract class Pengguna {
    // Enkapsulasi: Semua atribut private
    private int id;
    private String username;
    private String password;
    private String namaLengkap;
    private String email;
    private String role; // "admin" atau "user"

    // Constructor Kosong
    public Pengguna() {
    }

    // Constructor Lengkap
    public Pengguna(int id, String username, String password, String namaLengkap, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.role = role;
    }

    // Abstract Method (Polimorfisme)
    // Setiap anak (User/Admin) WAJIB punya menu, tapi isinya beda-beda
    public abstract void tampilkanMenu();

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}