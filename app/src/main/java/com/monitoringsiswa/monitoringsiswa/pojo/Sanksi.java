package com.monitoringsiswa.monitoringsiswa.pojo;

/**
 * Created by rahardyan on 08/05/16.
 */
public class Sanksi {
    private final int id;
    private final String namaSanksi;
    private final int batasBawah;
    private final int batasAtas;


    public Sanksi(int id, String namaSanksi, int batasBawah, int batasAtas) {
        this.id = id;
        this.namaSanksi = namaSanksi;
        this.batasBawah = batasBawah;
        this.batasAtas = batasAtas;
    }

    public int getId() {
        return id;
    }

    public String getNamaSanksi() {
        return namaSanksi;
    }

    public int getBatasBawah() {
        return batasBawah;
    }

    public int getBatasAtas() {
        return batasAtas;
    }
}
