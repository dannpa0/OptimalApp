package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.fragment.BerandaFragment;
import com.example.pelangiaquscape.fragment.PemberitahuanFragment;
import com.example.pelangiaquscape.fragment.ProfileFragment;
import com.example.pelangiaquscape.fragment.RekapFragment;
import com.example.pelangiaquscape.fragment.StokFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    final String EXTRA = "INTENT_EDIT_TO_MAIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new BerandaFragment();
//                            Toast.makeText(MainActivity.this, "Home",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_rekap:
                            selectedFragment = new RekapFragment();
//                            Toast.makeText(MainActivity.this, "Rekap Kas",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_stok:
                            selectedFragment = new StokFragment();
//                            Toast.makeText(MainActivity.this, "Stok Barang",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_inbox:
                            selectedFragment = new PemberitahuanFragment();
//                            Toast.makeText(MainActivity.this, "Pemberitahuan",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_profile:
//                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
//                            editor.putString("profileid", FirebaseAuth.getIntance().getCurrentUser().getUid());
//                            editor.apply();
                            selectedFragment = new ProfileFragment();
//                            Toast.makeText(MainActivity.this, "Profile",Toast.LENGTH_SHORT).show();
                            break;
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }

                    return true;
                }
            };
}
