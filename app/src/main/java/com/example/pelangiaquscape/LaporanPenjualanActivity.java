package com.example.pelangiaquscape;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaporanPenjualanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_penjualan);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Bulanan"));
        tabLayout.addTab(tabLayout.newTab().setText("Tahunan"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        PageAdapterLaporan pageAdapterLaporan = new PageAdapterLaporan(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pageAdapterLaporan);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }
}
