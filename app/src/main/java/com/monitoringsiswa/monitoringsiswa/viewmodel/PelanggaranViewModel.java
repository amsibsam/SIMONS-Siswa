package com.monitoringsiswa.monitoringsiswa.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.monitoringsiswa.monitoringsiswa.pojo.Pelanggaran;

import butterknife.Bind;

/**
 * Created by rahardyan on 24/04/16.
 */
public class PelanggaranViewModel extends BaseObservable{
    private Pelanggaran pelanggaran;
    private View.OnClickListener pelanggaranDetailClickListener;

    public PelanggaranViewModel(Pelanggaran pelanggaran, View.OnClickListener pelanggaranDetailClickListener) {
        this.pelanggaran = pelanggaran;
        this.pelanggaranDetailClickListener = pelanggaranDetailClickListener;
    }

    @Bindable
    public String getNamaGuru() {
        return pelanggaran.getNamaGuru();
    }

    @Bindable
    public String getTanggal() {
        return pelanggaran.getTanggal();
    }

    @Bindable
    public String getKategori(){
        return pelanggaran.getNamaKategoriPelanggaran();
    }

    @Bindable
    public String getPoin(){
        return String.valueOf(pelanggaran.getPoin());
    }

    @Bindable
    public String getNamaPelanggaran(){
        return pelanggaran.getNamaPoinPelanggaran();
    }

}
