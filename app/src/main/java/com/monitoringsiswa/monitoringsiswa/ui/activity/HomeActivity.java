package com.monitoringsiswa.monitoringsiswa.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.monitoringsiswa.monitoringsiswa.R;
import com.monitoringsiswa.monitoringsiswa.databinding.ActivityHomeBinding;
import com.monitoringsiswa.monitoringsiswa.ui.adapter.TabAdapter;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        tabLayout = binding.tabLayout;
        viewPager = binding.pager;

        setSupportActionBar(binding.toolbar);
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_home));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_sanksi));

        final PagerAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        binding.toolbarTitle.setText("SIMONS WALI HOME");
                        break;
                    case 1:
                        binding.toolbarTitle.setText("SANKSI");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
