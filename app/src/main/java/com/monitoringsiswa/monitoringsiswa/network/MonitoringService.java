package com.monitoringsiswa.monitoringsiswa.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.monitoringsiswa.monitoringsiswa.pojo.ProfilSiswa;
import com.monitoringsiswa.monitoringsiswa.pojo.Sanksi;
import com.monitoringsiswa.monitoringsiswa.pojo.Siswa;
import com.monitoringsiswa.monitoringsiswa.pojo.KategoriPelanggaran;
import com.monitoringsiswa.monitoringsiswa.pojo.Pelanggaran;
import com.monitoringsiswa.monitoringsiswa.pojo.PoinPelanggaran;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.BaseUrl;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by root on 09/04/16.
 */
public class MonitoringService {
    private interface KilapApi {
        @GET("siswa/pelanggaran/{id}")
        Observable<PelanggaranResponse> getPelanggaran(@Path("id") int id);

        @GET("guru/kategori-pelanggaran")
        Observable<KategoriPelanggaranResponse> getKategoriPelanggaran();

        @GET("guru/poin-pelanggaran/{id}")
        Observable<PoinPelanggaranResponse> getPoinPelanggaran(@Path("id") int id);

        @GET("siswa/sanksi")
        Observable<SanksiResponse> getSanksi();

        @GET("siswa/poin/{id}")
        Observable<PoinResponse> getPoin(@Path("id") int id);

        @GET("siswa/profil/{id}")
        Observable<ProfileResponse> getProfil(@Path("id") int id);


        @FormUrlEncoded
        @POST("siswa/login")
        Observable<LoginResponse> login(@Field("nis") String nis,
                                        @Field("password") String password);
        @FormUrlEncoded
        @POST("guru/pelanggaran")
        Observable<PostPelanggaranResponse> postPelanggaran(@Field("tanggal") String tanggal,
                                                            @Field("catatan") String catatan,
                                                            @Field("nis") String nis,
                                                            @Field("guru_id") int guruId,
                                                            @Field("poin_pelanggaran_id") int poinPelanggaranId);

    }

    private KilapApi kilapApi;

    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public MonitoringService(Context context){
        // 1. Prepare the baseUrl. We will dynamically resolve this from the SharedPreferences.
        BaseUrl baseUrl = new BaseUrl() {
            @Override
            public HttpUrl url() {
                final String baseUrl = "http://si-monitoring.herokuapp.com/api/";
                return HttpUrl.parse(baseUrl);
            }
        };

        // 2. Create and prepare the Client that will be the "backend".
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120, TimeUnit.SECONDS);

        // 3. Create and prepare the logging interceptor. This is mainly for debugging purpose.
        // TBD: Is it better to use Stetho instead?
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("retrofit", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(loggingInterceptor);

        // 4. Almost done. Now, we can create the Retrofit instance.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        // 5. Finally, we can create the model of the API.
        kilapApi = retrofit.create(KilapApi.class);


    }

    public Observable<Siswa> login(String nis, String password){
        return kilapApi.login(nis, password)
                .map(new Func1<LoginResponse, Siswa>() {
                    @Override
                    public Siswa call(LoginResponse loginResponse) {
                        return loginResponse.data.siswa.toSiswaPojo();
                    }
                });
    }

    public Observable<Pelanggaran> getPelanggaran(int id){
        return kilapApi.getPelanggaran(id)
                .flatMap(new Func1<PelanggaranResponse, Observable<PelanggaranResponse.Data.Pelanggaran>>() {
                    @Override
                    public Observable<PelanggaranResponse.Data.Pelanggaran> call(PelanggaranResponse pelanggaranResponse) {
                        return Observable.from(pelanggaranResponse.data.pelanggaran);
                    }
                })
                .map(new Func1<PelanggaranResponse.Data.Pelanggaran, Pelanggaran>() {
                    @Override
                    public Pelanggaran call(PelanggaranResponse.Data.Pelanggaran pelanggaran) {
                        return pelanggaran.toPelanggaranPojo();
                    }
                });
    }

    public Observable<Sanksi> getSanksi(){
        return kilapApi.getSanksi()
                .flatMap(new Func1<SanksiResponse, Observable<SanksiResponse.Data.Sanksi>>() {
                    @Override
                    public Observable<SanksiResponse.Data.Sanksi> call(SanksiResponse sanksiResponse) {
                        return Observable.from(sanksiResponse.data.sanksi);
                    }
                })
                .map(new Func1<SanksiResponse.Data.Sanksi, Sanksi>() {
                    @Override
                    public Sanksi call(SanksiResponse.Data.Sanksi sanksi) {
                        return sanksi.toSansiPojo();
                    }
                });
    }

    public Observable<Integer> getPoin(int idSiswa){
        return kilapApi.getPoin(idSiswa)
                .map(new Func1<PoinResponse, Integer>() {
                    @Override
                    public Integer call(PoinResponse poinResponse) {
                        return poinResponse.data.total_poin;
                    }
                });
    }

    public Observable<ProfilSiswa> getProfil(int idSiswa){
        return kilapApi.getProfil(idSiswa)
                .map(new Func1<ProfileResponse, ProfilSiswa>() {
                    @Override
                    public ProfilSiswa call(ProfileResponse profileResponse) {
                        return profileResponse.data.siswa.toProfileSiswa();
                    }
                });
    }

    public Observable<KategoriPelanggaran> getKategoriPelanggaran(){
        return kilapApi.getKategoriPelanggaran()
                .flatMap(new Func1<KategoriPelanggaranResponse, Observable<KategoriPelanggaranResponse.Data.Kategori>>() {
                    @Override
                    public Observable<KategoriPelanggaranResponse.Data.Kategori> call(KategoriPelanggaranResponse kategoriPelanggaranResponse) {
                        return Observable.from(kategoriPelanggaranResponse.data.kategoriPelanggaran);
                    }
                })
                .map(new Func1<KategoriPelanggaranResponse.Data.Kategori, KategoriPelanggaran>() {
                    @Override
                    public KategoriPelanggaran call(KategoriPelanggaranResponse.Data.Kategori kategori) {
                        return kategori.toKategoriPelanggaranPojo();
                    }
                });
    }

    public Observable<PoinPelanggaran> getPoinPelanggaran(int id){
        return kilapApi.getPoinPelanggaran(id)
                .flatMap(new Func1<PoinPelanggaranResponse, Observable<PoinPelanggaranResponse.Data.PoinPelanggaran>>() {
                    @Override
                    public Observable<PoinPelanggaranResponse.Data.PoinPelanggaran> call(PoinPelanggaranResponse poinPelanggaranResponse) {
                        return Observable.from(poinPelanggaranResponse.data.poinPelanggaran);
                    }
                })
                .map(new Func1<PoinPelanggaranResponse.Data.PoinPelanggaran, PoinPelanggaran>() {
                    @Override
                    public PoinPelanggaran call(PoinPelanggaranResponse.Data.PoinPelanggaran poinPelanggaran) {
                        return poinPelanggaran.toPoinPelanggaranPojo();
                    }
                });
    }

    public Observable<String> postPelanggaran(String catatan, String nis, int guruId, final int poinPelanggaranId){
//        TODO:make date not a hardcoded
        return kilapApi.postPelanggaran("2016-04-21", catatan, nis, guruId, poinPelanggaranId)
                .map(new Func1<PostPelanggaranResponse, String>() {
                    @Override
                    public String call(PostPelanggaranResponse postPelanggaranResponse) {
                        return postPelanggaranResponse.message;
                    }
                });
    }

    private class LoginResponse{
        Data data;

        class Data{
            Siswa siswa;

            class Siswa{
                int id;
                String nis;
                String namaSiswa;
                String jenisKelamin;
                String nomorHp;
                String alamat;
                int kelasId;

                com.monitoringsiswa.monitoringsiswa.pojo.Siswa toSiswaPojo(){
                    return new com.monitoringsiswa.monitoringsiswa.pojo.Siswa(id, nis, namaSiswa, jenisKelamin, nomorHp, alamat, kelasId);
                }
            }
        }

    }


    private class PelanggaranResponse{
        Data data;

        class Data{
            List<Pelanggaran> pelanggaran;

            class Pelanggaran{
                String tanggal;
                String namaPoinPelanggaran;
                int poin;
                String namaGuru;
                String namaKategoriPelanggaran;


                com.monitoringsiswa.monitoringsiswa.pojo.Pelanggaran toPelanggaranPojo(){
                    return new com.monitoringsiswa.monitoringsiswa.pojo.Pelanggaran(tanggal, namaPoinPelanggaran, poin, namaGuru, namaKategoriPelanggaran);
                }
            }
        }
    }

    private class KategoriPelanggaranResponse{
        String status;
        Data data;

        class Data{
            List<Kategori> kategoriPelanggaran;

            class Kategori{
                int id;
                String namaKategoriPelanggaran;

                KategoriPelanggaran toKategoriPelanggaranPojo(){
                    return new KategoriPelanggaran(id, namaKategoriPelanggaran);
                }
            }


        }

    }

    private class PoinPelanggaranResponse{
        String status;
        Data data;

        class Data{
            List<PoinPelanggaran> poinPelanggaran;

            class PoinPelanggaran{
                int id;
                String namaPoinPelanggaran;
                int poin;
                int kategoriPelanggaranId;

                com.monitoringsiswa.monitoringsiswa.pojo.PoinPelanggaran toPoinPelanggaranPojo(){
                    return new com.monitoringsiswa.monitoringsiswa.pojo.PoinPelanggaran(id, namaPoinPelanggaran, poin, kategoriPelanggaranId);
                }
            }
        }
    }

    private class PostPelanggaranResponse{
        String status;
        String message;
    }

    private class SanksiResponse{
        String status;
        Data data;

        class Data{
            List<Sanksi> sanksi;

            class Sanksi{
                int id;
                String nama_sanksi;
                int batas_bawah;
                int batas_atas;

                com.monitoringsiswa.monitoringsiswa.pojo.Sanksi toSansiPojo(){
                    return new com.monitoringsiswa.monitoringsiswa.pojo.Sanksi(id, nama_sanksi, batas_bawah, batas_atas);
                }
            }
        }
    }

    private class PoinResponse{
        String status;
        Data data;

        class Data{
            int total_poin;
        }
    }

    private class ProfileResponse{
        String status;
        Data data;

        class Data{
            Siswa siswa;

            class Siswa{
                String nama_siswa;
                String nama_kelas;
                String nama_wali_kelas;

                ProfilSiswa toProfileSiswa(){
                    return new ProfilSiswa(nama_siswa, nama_kelas, nama_wali_kelas);
                }
            }

        }
    }

}
