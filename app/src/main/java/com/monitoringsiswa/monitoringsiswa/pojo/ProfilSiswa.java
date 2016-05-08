package com.monitoringsiswa.monitoringsiswa.pojo;

/**
 * Created by rahardyan on 08/05/16.
 */
public class ProfilSiswa {
    private final String namaSiswa;
    private final String namaKelas;
    private final String namaWaliKelas;


    public ProfilSiswa(String namaSiswa, String namaKelas, String namaWaliKelas) {
        this.namaSiswa = namaSiswa;
        this.namaKelas = namaKelas;
        this.namaWaliKelas = namaWaliKelas;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public String getNamaWaliKelas() {
        return namaWaliKelas;
    }
}
