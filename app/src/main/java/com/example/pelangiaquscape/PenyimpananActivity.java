package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.PenyimpananAdapter;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.ViewHolder.PenyimpananViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class PenyimpananActivity extends AppCompatActivity {

    ImageView cancel;
    FloatingActionButton fab_penyimpanan;
    LinearLayout imageLayout;
    RecyclerView rvPenyimpanan;

    List<String> listKey;
    List<Penyimpanan> listPenyimpanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyimpanan);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        fab_penyimpanan = findViewById(R.id.fab_penyimpanan);
        imageLayout = findViewById(R.id.linear_imageview_penyimpanan);
        rvPenyimpanan = findViewById(R.id.rv_inventory);
        rvPenyimpanan.setHasFixedSize(true);


        fab_penyimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(PenyimpananActivity.this, "Tambah Inventory", Toast.LENGTH_SHORT).show();
                Intent fab_penyimpanan = new Intent(PenyimpananActivity.this, TambahPenyimpananActivity.class);
                startActivity(fab_penyimpanan);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadPenyimpanan();
    }

    void loadPenyimpanan(){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("Penyimpanan");


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listPenyimpanan = new ArrayList<>();
                listKey = new ArrayList<>();


                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Penyimpanan penyimpanan = ds.getValue(Penyimpanan.class);
                    String key = dataSnapshot.getKey();

                    listPenyimpanan.add(penyimpanan);
                    listKey.add(key);
                }

                PenyimpananAdapter adapter = new PenyimpananAdapter(PenyimpananActivity.this, listPenyimpanan, listKey);

                LinearLayoutManager manager = new LinearLayoutManager(PenyimpananActivity.this);
                manager.setReverseLayout(true);
                manager.setStackFromEnd(true);

                rvPenyimpanan.setAdapter(adapter);
                rvPenyimpanan.setLayoutManager(manager);

                if(adapter.getItemCount() > 0){
                    imageLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
