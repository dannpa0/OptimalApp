package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.pelangiaquscape.Model.Pegawai;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PegawaiViewHolder;

import java.util.List;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiViewHolder> {

    Context context;
    List<Pegawai> pegawai;

    public PegawaiAdapter(Context context, List<Pegawai> pegawai) {
        this.context = context;
        this.pegawai = pegawai;
    }

    @NonNull
    @Override
    public PegawaiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pegawai, viewGroup, false);
        return new PegawaiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PegawaiViewHolder pegawaiViewHolder, int i) {
        pegawaiViewHolder.bindData(pegawai.get(i));

    }

    @Override
    public int getItemCount() {
        return pegawai.size();
    }
}
