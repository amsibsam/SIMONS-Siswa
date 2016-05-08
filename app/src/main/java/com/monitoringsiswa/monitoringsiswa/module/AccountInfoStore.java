package com.monitoringsiswa.monitoringsiswa.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.monitoringsiswa.monitoringsiswa.pojo.Siswa;

/**
 * Created by rahardyan on 24/04/16.
 */
public class AccountInfoStore {
    private static final String TAG = AccountInfoStore.class.getSimpleName();

    private final SharedPreferences sharedPreferences;

    public AccountInfoStore(Context context){
        this.sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public boolean hasAccount(){
        return sharedPreferences.contains("id");
    }

    public void cacheAccountInfo(Siswa siswa){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", siswa.getId());
        editor.putString("nis", siswa.getNis());
        editor.putString("name", siswa.getNamaSiswa());
        editor.putString("no_hp", siswa.getNomorHp());
        editor.putString("jenis_kelamin", siswa.getJenisKelamin());
        editor.putString("alamat", siswa.getAlamat());
        editor.putInt("kelas_id", siswa.getKelasId());
        editor.commit();
    }

    public Siswa getSiswaAccount(){
        int id = sharedPreferences.getInt("id", 1);
        String nis = sharedPreferences.getString("nis", "");
        String name = sharedPreferences.getString("name", "");
        String jenisKelamin = sharedPreferences.getString("jenis_kelamin", "");
        String nomorHp = sharedPreferences.getString("no_hp", "");
        String alamat = sharedPreferences.getString("alamat", "");
        int kelasId = sharedPreferences.getInt("kelas_id", -1);
        return new Siswa(id, nis, name, jenisKelamin, nomorHp, alamat, kelasId);
    }
}
