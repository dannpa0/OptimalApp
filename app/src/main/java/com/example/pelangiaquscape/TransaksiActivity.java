package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.ViewHolder.TransaksiMerekViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TransaksiActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseMerek;
    FirebaseRecyclerAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Query query;
    ImageView cancel;
    ImageView keranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        cancel =  findViewById(R.id.im_cancel);
        keranjang = findViewById(R.id.im_keranjang);

        //init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseMerek = firebaseDatabase.getReference("Merek");

        //load data merek
        recyclerView = findViewById(R.id.rv_daftar_merek);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keranjang = new Intent(TransaksiActivity.this, KeranjangPenjualanActivity.class);
                startActivity(keranjang);
            }
        });

        loadMerk();

    }

    private void loadMerk() {
        query = FirebaseDatabase.getInstance().getReference().child("Merek");
        FirebaseRecyclerOptions<Merek> options =
                new FirebaseRecyclerOptions.Builder<Merek>().setQuery(query, Merek.class).build();

        Log.i("SNAPSHOT", options.getSnapshots().toString());

        adapter = new FirebaseRecyclerAdapter<Merek, TransaksiMerekViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TransaksiMerekViewHolder holder, int position, @NonNull final Merek model) {
                holder.tv_merek.setText(model.getNama());
//                holder.im_arrow.setImageResource(R.drawable.ic_arrow_black);

                Log.i("INFORMATION", model.getNama()+" "+model.getNama());
                final Merek clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent merek = new Intent(TransaksiActivity.this, TransaksiKodeBarangActivity.class);
                        merek.putExtra("idMerek", adapter.getRef(position).getKey());
                        merek.putExtra("namaMerek", model.getNama());
                        Log.i("GET IDMEREK", merek.getStringExtra("idMerek") + adapter.getRef(position).getKey());
                        startActivity(merek);
                    }
                });
            }

            @NonNull
            @Override
            public TransaksiMerekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_merk_barang_transaksi, viewGroup, false);
                Log.i("Buat View Holder", view.toString());
                return new TransaksiMerekViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
