package com.monitoringsiswa.monitoringsiswa.pojo;

/**
 * Created by rahardyan on 24/04/16.
 */
public class Pelanggaran {
    private final String tanggal;
    private final String namaPoinPelanggaran;
    private final int poin;
    private final String namaGuru;
    private final String namaKategoriPelanggaran;

    public Pelanggaran(String tanggal, String namaPoinPelanggaran, int poin, String namaGuru, String namaKategoriPelanggaran) {
        this.tanggal = tanggal;
        this.namaPoinPelanggaran = namaPoinPelanggaran;
        this.poin = poin;
        this.namaGuru = namaGuru;
        this.namaKategoriPelanggaran = namaKategoriPelanggaran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNamaPoinPelanggaran() {
        return namaPoinPelanggaran;
    }

    public int getPoin() {
        return poin;
    }

    public String getNamaGuru() {
        return namaGuru;
    }

    public String getNamaKategoriPelanggaran() {
        return namaKategoriPelanggaran;
    }
}
