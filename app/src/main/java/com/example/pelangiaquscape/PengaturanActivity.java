package com.example.pelangiaquscape;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PengaturanActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;

    ImageView cancel;
    LinearLayout pengaturanPrinter;
    TextView statusPrinter;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

       //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        statusPrinter = findViewById(R.id.status_printer);
        pengaturanPrinter = findViewById(R.id.printer);

        //adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //on bluetooth
        pengaturanPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()){
                    showToast("Menyalakan Bluetooth...");
                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);

                } else {
                    Intent cariPrinter = new Intent(PengaturanActivity.this, PrinterActivity.class);
                    startActivity(cariPrinter);
                    showToast("Bluetooth sudah nyala");
                }
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (requestCode == RESULT_OK){
                    //bluetooth is on

                    showToast("Bluetooth Aktif");


                } else {
                    //user denied to turn bluetooth on
                    showToast("Tidak bisa menyalakan bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
