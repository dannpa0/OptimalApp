package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.KartuPelanggan;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PelangganViewHolder;

import java.util.List;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganViewHolder> {



    Context context;
    List<Pelanggan> pelanggan;

    public PelangganAdapter(Context context, List<Pelanggan> pelanggan) {
        this.context = context;
        this.pelanggan = pelanggan;
    }

    @NonNull
    @Override
    public PelangganViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pelanggan, viewGroup, false);
        return new PelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PelangganViewHolder pelangganViewHolder, int i) {
        pelangganViewHolder.bindData(pelanggan.get(i));
        KartuPelanggan kartuPelanggan = pelanggan.get(i);


        pelangganViewHolder.tv_nama_pelanggan.setText(kartuPelanggan.getNamaPelanggan());
        pelangganViewHolder.tv_noHp_pelanggan.setText(kartuPelanggan.getNoHpPelanggan());
        pelangganViewHolder.tv_alamat_pelanggan.setText(kartuPelanggan.getAlamatPelanggan());
//        pelangganViewHolder.tv_nama_toko.setText(kartuPelanggan.getNamaToko());
//        pelangganViewHolder.tv_noTelepon_toko.setText(kartuPelanggan.getNoHpToko());
//        pelangganViewHolder.tv_email_toko.setText(kartuPelanggan.getEmailToko());
//        pelangganViewHolder.tv_alamat_toko.setText(kartuPelanggan.getAlamatToko());
//
    }

    @Override
    public int getItemCount() {
        return pelanggan.size();
    }
}
