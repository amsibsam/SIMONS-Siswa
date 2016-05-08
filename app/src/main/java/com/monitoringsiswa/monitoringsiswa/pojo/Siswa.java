package com.monitoringsiswa.monitoringsiswa.pojo;

/**
 * Created by rahardyan on 24/04/16.
 */
public class Siswa {
    private final int id;
    private final String nis;
    private final String namaSiswa;
    private final String jenisKelamin;
    private final String nomorHp;
    private final String alamat;
    private final int kelasId;

    public Siswa(int id, String nis, String namaSiswa, String jenisKelamin, String nomorHp, String alamat, int kelasId) {
        this.id = id;
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.jenisKelamin = jenisKelamin;
        this.nomorHp = nomorHp;
        this.alamat = alamat;
        this.kelasId = kelasId;
    }

    public int getId() {
        return id;
    }

    public String getNis() {
        return nis;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getNomorHp() {
        return nomorHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getKelasId() {
        return kelasId;
    }
}
