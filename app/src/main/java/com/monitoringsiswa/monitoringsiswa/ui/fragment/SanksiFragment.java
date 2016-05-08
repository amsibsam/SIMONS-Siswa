package com.monitoringsiswa.monitoringsiswa.ui.fragment;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.monitoringsiswa.monitoringsiswa.MonitoringApplication;
import com.monitoringsiswa.monitoringsiswa.R;
import com.monitoringsiswa.monitoringsiswa.databinding.FragmentInputPelanggaranBinding;
import com.monitoringsiswa.monitoringsiswa.module.AccountInfoStore;
import com.monitoringsiswa.monitoringsiswa.network.MonitoringService;
import com.monitoringsiswa.monitoringsiswa.pojo.ProfilSiswa;
import com.monitoringsiswa.monitoringsiswa.pojo.Sanksi;
import com.monitoringsiswa.monitoringsiswa.ui.adapter.KategoriSpinnerAdapter;
import com.monitoringsiswa.monitoringsiswa.ui.adapter.PoinSpinnerAdapter;
import com.monitoringsiswa.monitoringsiswa.viewmodel.SanksiViewModel;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SanksiFragment extends android.support.v4.app.Fragment {
    private static HomeFragment homeFragmentData;

    @Inject
    MonitoringService monitoringService;

    @Inject
    AccountInfoStore accountInfoStore;

    private FragmentInputPelanggaranBinding binding;

    private SanksiListViewModel sanksiListViewModel = new SanksiListViewModel();

    public SanksiFragment() {
        // Required empty public constructor
    }

    public static SanksiFragment newInstance(HomeFragment homeFragment) {
        Bundle args = new Bundle();

        homeFragmentData = homeFragment;

        SanksiFragment fragment = new SanksiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonitoringApplication.getComponent().inject(this);
        getProfil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInputPelanggaranBinding.inflate(inflater, container, false);


        // Inflate the layout for this fragment

        binding.setItemSanksiListViewModel(sanksiListViewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getSanksi();
    }

    public static class SanksiListViewModel{
        public final ObservableList<SanksiViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemSanksiViewModel, R.layout.item_sanksi);
    }

    private void getProfil(){
        monitoringService.getProfil(accountInfoStore.getSiswaAccount().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfilSiswa>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProfilSiswa profilSiswa) {
                        binding.tvNama.setText(profilSiswa.getNamaSiswa());
                        binding.tvKelas.setText(profilSiswa.getNamaKelas());
                        binding.tvNamaGuruWali.setText(profilSiswa.getNamaWaliKelas());
                    }
                });
    }

    private void getSanksi(){
        monitoringService.getPoin(accountInfoStore.getSiswaAccount().getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error on getPoin "+e.toString());
                    }

                    @Override
                    public void onNext(final Integer poinFromServer) {
                        binding.tvPoinTotal.setText(poinFromServer.toString());
                        sanksiListViewModel.items.clear();
                        monitoringService.getSanksi()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<Sanksi>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("amsibsam", "error getSanksi "+e.toString());
                                    }

                                    @Override
                                    public void onNext(Sanksi sanksi) {
                                        sanksiListViewModel.items.add(new SanksiViewModel(sanksi, poinFromServer));
                                    }
                                });
                    }
                });
    }


}
