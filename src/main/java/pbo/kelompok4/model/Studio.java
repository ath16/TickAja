package pbo.kelompok4.model;

public class Studio {
    private int id;
    private int lokasiId; // Foreign key ke LokasiBioskop
    private String namaStudio;

    // 1. Constructor Kosong (Wajib untuk DAO)
    public Studio() {
    }

    // 2. Constructor Lengkap
    public Studio(int id, int lokasiId, String namaStudio) {
        this.id = id;
        this.lokasiId = lokasiId;
        this.namaStudio = namaStudio;
    }

    // 3. Getter dan Setter (Wajib ada)
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLokasiId() {
        return lokasiId;
    }

    public void setLokasiId(int lokasiId) {
        this.lokasiId = lokasiId;
    }

    public String getNamaStudio() {
        return namaStudio;
    }

    public void setNamaStudio(String namaStudio) {
        this.namaStudio = namaStudio;
    }
    
    // 4. toString (PENTING: Agar muncul nama studio di ComboBox, bukan alamat memori)
    @Override
    public String toString() {
        return namaStudio;
    }
}