package pbo.kelompok4.model;

public class Studio {
    private int id;
    private int lokasiId; // Foreign key ke LokasiBioskop (jika perlu nanti)
    private String namaStudio;

    public Studio() {
    }

    public Studio(int id, int lokasiId, String namaStudio) {
        this.id = id;
        this.lokasiId = lokasiId;
        this.namaStudio = namaStudio;
    }

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
    
    @Override
    public String toString() {
        return namaStudio;
    }
}