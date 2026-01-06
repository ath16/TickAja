package pbo.kelompok4.model;

public class LokasiBioskop {
    
    private int idLokasi;
    private String namaLokasi;
    private String alamat;

    public LokasiBioskop(int idLokasi, String namaLokasi, String alamat){
        this.idLokasi = idLokasi;
        this.namaLokasi = namaLokasi;
        this.alamat = alamat;
    }

    public LokasiBioskop(String namaLokasi, String alamat) {
        this.namaLokasi = namaLokasi;
        this.alamat = alamat;
    }

    public int getIdLokasi(){
        return idLokasi;
    }

    public String getNamaLokasi(){
        return namaLokasi;
    }

    public String getAlamat(){
        return alamat;
    }

}
