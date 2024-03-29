package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Pegawai;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TambahPegawaiActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    CircleImageView fotoPegawai, add_foto;
    Spinner spinnerHakAkses;
    TextInputEditText etNamaPegawai, etNamaPengguna,etKataSandi, etJabatan, etNoHp, etEmailPegawai;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    Pegawai pegawai;
    String idPegawai;

    private static final int IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageTask uploadTask;

    String namaPegawai, namaPengguna, kataSandi, jabatan, hakAkses, noHp, emailPegawai, kodeLogin, fotoProfilePegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pegawai);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNamaPegawai = findViewById(R.id.et_nama_pegawai);
        fotoPegawai = findViewById(R.id.image_profile);
        add_foto = findViewById(R.id.add_foto);
        etNamaPengguna = findViewById(R.id.et_nama_pengguna_pegawai);
        etKataSandi = findViewById(R.id.et_password);
        etJabatan = findViewById(R.id.et_jabatan);
        etNoHp = findViewById(R.id.et_no_hp_pegawai);
        etEmailPegawai = findViewById(R.id.et_email_pegawai);
        spinnerHakAkses = findViewById(R.id.spinner_hak_akses);

        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pegawai");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("foto pegawai");

        pegawai = new Pegawai();


        try {
            idPegawai = getIntent().getExtras().getString("idForPegawai");
            System.out.println("ID in tambah pegawai act " + idPegawai);
        } catch (NullPointerException ex){
            idPegawai = "1";
        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,R.array.akses_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerHakAkses.setAdapter(adapter);

//        boolean fromPegawaiFragment = getIntent().getExtras().getBoolean("fromPegawaiFragment", false);
//        if (fromPegawaiFragment){
//
//
//            fotoProfilePegawai = getIntent().getExtras().getString("fotoPegawai");
//            namaPegawai = getIntent().getExtras().getString("namaPegawai");
//            namaPengguna = getIntent().getExtras().getString("namapengguna");
//            kataSandi = getIntent().getExtras().getString("katasandi");
//            jabatan = getIntent().getExtras().getString("jabatan");
//            hakAkses = getIntent().getExtras().getString("hakAkses");
//            noHp = getIntent().getExtras().getString("noHp");
//            emailPegawai = getIntent().getExtras().getString("emailPegawai");
//            bind(fotoProfilePegawai, namaPegawai, namaPengguna, kataSandi, jabatan, hakAkses, noHp,emailPegawai);
//
//        }

        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        add_foto.setOnClickListener(this);

    }

    private void bind(final String fotoProfilePegawai,
                      String namaPegawai,
                      String namaPengguna,
                      String kataSandi,
                      String jabatan,
                      String hakAkses,
                      String noHp,
                      String emailPegawai){

        int idxHakAkses = -1;

        String[] aksesArray = getResources().getStringArray(R.array.akses_arrays);

        for (int i = 0; i < aksesArray.length; i++){
            if (aksesArray[i].equals(hakAkses)){
                idxHakAkses = i;
            }
        }




        etNamaPegawai.setText(namaPegawai);
        etNamaPengguna.setText(namaPengguna);
        etKataSandi.setText(kataSandi);
        etJabatan.setText(jabatan);
        spinnerHakAkses.setSelection(idxHakAkses);
        etNoHp.setText(noHp);
        etEmailPegawai.setText(emailPegawai);

    }

    private void save() {

        spinnerHakAkses.getSelectedItem().toString();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                namaPegawai = "" + dataSnapshot.child("namaPegawai").getValue();
//                namaPengguna = "" + dataSnapshot.child("namapengguna").getValue();
//                kataSandi = "" + dataSnapshot.child("katasandi").getValue();
//                jabatan = "" + dataSnapshot.child("jabatan").getValue();
////                kodeLogin = "" + dataSnapshot.child("kode_login").getValue();
////                switch (kodeLogin) {
////                    case "0":
////                        hakAkses = "Super Admin";
////                        break;
////                    case "1":
////                        hakAkses = "Pegawai";
////                        break;
////                }
//
//                noHp = "" + dataSnapshot.child("noHp").getValue();
//                emailPegawai = "" + dataSnapshot.child("emailPegawai").getValue();


                getValues();
                databaseReference.child(String.valueOf(idPegawai)).setValue(pegawai);


                try {
                    Picasso.get().load(fotoProfilePegawai).into(fotoPegawai);
                } catch (IllegalArgumentException e){
                    fotoPegawai.setImageResource(R.drawable.ic_foto_profile_edit);
                }


                Toast.makeText(TambahPegawaiActivity.this, "Pegawai telah ditambah", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//
    private void getValues(){
        pegawai.setFotoPegawai(fotoPegawai.getDrawable().toString());
        pegawai.setNamaPegawai(etNamaPegawai.getText().toString());
        pegawai.setNamapengguna(etNamaPengguna.getText().toString());
        pegawai.setPassword(etKataSandi.getText().toString());
        pegawai.setJabatan(etJabatan.getText().toString());
        pegawai.setHakAkses(spinnerHakAkses.getSelectedItem().toString());
        pegawai.setNoHp(etNoHp.getText().toString());
        pegawai.setEmailPegawai(etEmailPegawai.getText().toString());

    }


    private void ubah_foto() {
        openImage();
    }

    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));


    }

    private void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Diunggah");
        progressDialog.show();

        if (mImageUri != null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                   if (!task.isSuccessful()){
                       throw task.getException();
                   }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

//                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pegawai").child(firebaseUser.getUid());
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this,"Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && requestCode == RESULT_OK
        && data != null && data.getData() != null){
            mImageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(), "Unggah sedang dalam proses", Toast.LENGTH_SHORT).show();

            } else {
                uploadImage();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:
                save();
                finish();
                break;
            case R.id.add_foto:
                ubah_foto();
                finish();
                break;
        }

    }




}
