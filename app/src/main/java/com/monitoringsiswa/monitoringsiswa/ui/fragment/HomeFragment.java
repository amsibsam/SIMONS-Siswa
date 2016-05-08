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
import android.widget.Toast;

import com.monitoringsiswa.monitoringsiswa.BR;
import com.monitoringsiswa.monitoringsiswa.MonitoringApplication;
import com.monitoringsiswa.monitoringsiswa.R;
import com.monitoringsiswa.monitoringsiswa.databinding.FragmentHomeBinding;
import com.monitoringsiswa.monitoringsiswa.module.AccountInfoStore;
import com.monitoringsiswa.monitoringsiswa.network.MonitoringService;
import com.monitoringsiswa.monitoringsiswa.pojo.Pelanggaran;
import com.monitoringsiswa.monitoringsiswa.viewmodel.PelanggaranViewModel;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment {
    private FragmentHomeBinding binding;
    private PelanggaranListViewModel pelanggaranListViewModel = new PelanggaranListViewModel();

    @Inject
    MonitoringService monitoringService;

    @Inject
    AccountInfoStore accountInfoStore;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonitoringApplication.getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        binding.setPelanggaranListViewModel(pelanggaranListViewModel);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPelanggaran(accountInfoStore.getSiswaAccount().getId());
    }

    public static class PelanggaranListViewModel{
        public final ObservableList<PelanggaranViewModel> items = new ObservableArrayList<>();
        public final ItemView itemView = ItemView.of(BR.itemViewModel, R.layout.item_siswa);
    }

    public void getPelanggaran(int id){
        binding.pbLoading.setVisibility(View.VISIBLE);
        pelanggaranListViewModel.items.clear();
        monitoringService.getPelanggaran(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Pelanggaran>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("amsibsam", "error getPelanggaran "+e.toString());
                    }

                    @Override
                    public void onNext(Pelanggaran pelanggaran) {
                        binding.pbLoading.setVisibility(View.GONE);
                        binding.listPelanggaran.setVisibility(View.VISIBLE);
                        pelanggaranListViewModel.items.add(new PelanggaranViewModel(pelanggaran,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                                    }
                                }));
                    }
                });
    }



}
