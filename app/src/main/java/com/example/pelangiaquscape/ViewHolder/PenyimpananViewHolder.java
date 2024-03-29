package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PenyimpananViewHolder extends RecyclerView.ViewHolder {

    private TextView tvKode, tvMerek, tvStok, tvSatuan, tvKeterangan, tvTanggal, tvJenisPenyimpanan;

    private LinearLayout ll;


    public PenyimpananViewHolder(@NonNull View v) {
        super(v);
        tvKode = v.findViewById(R.id.list_nama_barang_inventory);
        tvMerek = v.findViewById(R.id.merek_inventory);
        tvStok = v.findViewById(R.id.stok_barang_inventory);
        tvSatuan = v.findViewById(R.id.satuan_unit_barang_inventory);
        tvKeterangan = v.findViewById(R.id.keterangan_status_inventory);
        tvTanggal = v.findViewById(R.id.tgl_inventory);
        tvJenisPenyimpanan = v.findViewById(R.id.keterangan_barang_keluar);
        ll = v.findViewById(R.id.linear_ket);

    }

    public void bindData(Penyimpanan penyimpanan) {

        // GET CALENDAR FROM PENYIMPANAN
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(penyimpanan.getTimeInMilis());
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String dateFormat = format.format(date);


        // SET ALL TEXTVIEW FROM PENYIMPANAN
        tvKode.setText(penyimpanan.getKodeBarang());
        tvMerek.setText("unknown");
        BigDecimal de = new BigDecimal(penyimpanan.getJumlahBarang());
        tvStok.setText(de.toString());
        tvSatuan.setText("pcs");
        tvKeterangan.setText(penyimpanan.getKeteranganBarang());
        tvTanggal.setText(dateFormat);

        final int sdk = android.os.Build.VERSION.SDK_INT;

        switch(penyimpanan.getJenisPenyimpanan()){
            case 0:
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_masuk) );
                } else {
                    ll.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_masuk));
                }
                tvJenisPenyimpanan.setText("Barang Masuk");
                break;
            case 1:
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_keluar) );
                } else {
                    ll.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.keterangan_barang_keluar));
                }
                tvJenisPenyimpanan.setText("Barang Keluar");
                break;
        }

//        tvKode.setText(penyimpanan.getNamaBarang());
//        tvMerek.setText(penyimpanan.getNamaBarang());
    }
}
