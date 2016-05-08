package com.monitoringsiswa.monitoringsiswa.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.monitoringsiswa.monitoringsiswa.pojo.Sanksi;

/**
 * Created by rahardyan on 08/05/16.
 */
public class SanksiViewModel extends BaseObservable{
    private final Sanksi sanksi;
    private final int poinSiswa;


    public SanksiViewModel(Sanksi sanksi, int poinSiswa) {
        this.sanksi = sanksi;
        this.poinSiswa = poinSiswa;
    }

    @Bindable
    public String getNama(){
        return sanksi.getNamaSanksi();
    }

    @Bindable
    public String getBatasPoin(){
        return sanksi.getBatasBawah()+"/"+sanksi.getBatasAtas();
    }

    @Bindable
    public int getBatasBawah(){
        return sanksi.getBatasBawah();
    }

    @Bindable
    public int getBatasAtas(){
        return sanksi.getBatasBawah();
    }

    @Bindable
    public int getShouldVisible(){
        int visbility = View.VISIBLE;
        if (poinSiswa >= sanksi.getBatasBawah() && poinSiswa <= sanksi.getBatasAtas()){
            visbility = View.GONE;
        }
        return visbility;
    }

    @Bindable
    public boolean getShouldSetPoin(){
        boolean isTrue = false;
        if (poinSiswa >= sanksi.getBatasBawah() && poinSiswa <= sanksi.getBatasAtas()){
            isTrue = true;
        }
        return isTrue;
    }
}
