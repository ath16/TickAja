package pbo.kelompok4.model;

public class Studio {
    private int idStudio;
    private int idLokasi;
    private String namaStudio;

    public Studio(int idStudio, int idLokasi, String namaStudio){
        this.idStudio = idStudio;
        this.idLokasi = idLokasi;
        this.namaStudio = namaStudio;
    }

    public Studio(int idLokasithis, String namaStudio){
        this.idLokasi = idLokasi;
        this.namaStudio = namaStudio;
    }
    
    public int getIdStudio(){
        return idStudio;
    }

    public int getIdLokasi(){
        return idLokasi;
    }

    public String getNamaStudio(){
        return namaStudio;
    }
}
