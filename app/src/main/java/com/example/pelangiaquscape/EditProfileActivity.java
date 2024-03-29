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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.example.pelangiaquscape.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    final String EXTRA = "INTENT_EDIT_TO_MAIN";
    ImageView cancel, save, imgFotoprofile;
    TextView tvUbah;
    TextInputEditText etNamaAkun, etStatusJabatan, etNoHp, etBio;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri mImageUri;
    private StorageTask uploadTask;

    String namaPengguna, statusJabatan, biodata, fotoProfile, kodeLogin;
    private Uri currentPhotoUri;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        imgFotoprofile = findViewById(R.id.image_profile);
        tvUbah = findViewById(R.id.tv_ubah_foto);
        etNamaAkun = findViewById(R.id.et_nama_akun_pengguna);
        etStatusJabatan = findViewById(R.id.et_status_jabatan);
        etNoHp = findViewById(R.id.et_telepon);
        etBio = findViewById(R.id.et_bio);

        // INIT FIREBASE
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // REGISTER LISTENER
        cancel.setOnClickListener(this);
        tvUbah.setOnClickListener(this);

        loadProfile();
    }

    void loadProfile(){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("User");



        dr.child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                System.out.println("EditProfileActivity "+user1.getId());
                if(user1 != null) {
                    etNamaAkun.setText(user1.getUsername());
                    etNoHp.setText(user1.getTelepon());
                    etBio.setText(user1.getBio());
                    if (user1.getKodeLogin().equals("1")) {
                        etStatusJabatan.setText("Admin");
                    } else {
                        etStatusJabatan.setText("Super Admin");
                    }


                    user = user1;
                }

                save.setOnClickListener(EditProfileActivity.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fakturRef = storageRef.child("Profile").child(uid+".jpg");
        fakturRef.getDownloadUrl().addOnSuccessListener(uri -> {
            if(uri != null){
                currentPhotoUri = uri;
                Picasso.get().load(uri).into(imgFotoprofile);

            }
//            tvNamaFoto.setText(file.getName());
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            currentPhotoUri = uri;
            imgFotoprofile.setImageURI(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:
                uploadToCloudStorage();
                break;
            case R.id.image_profile:
                break;
            case R.id.tv_ubah_foto:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih file"), RESULT_LOAD_IMAGE);
                break;
        }
    }

    void uploadToCloudStorage() {


        Uri file = currentPhotoUri;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fakturRef = storageRef.child("Profile").child(user.getId()+".jpg");


        UploadTask uploadTask = fakturRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    ProgressDialog dialog = new ProgressDialog(EditProfileActivity.this);
                    dialog.setMessage("Sedang mengupload foto profil...");
                    dialog.setIndeterminate(false);
                    dialog.setProgress((int)progress);
                    dialog.show();

                })
                .addOnFailureListener(exception -> {
                    // Handle unsuccessful uploads
                })
                .addOnSuccessListener(taskSnapshot -> {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    Toast.makeText(this, "Upload Foto Profil berhasil", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                });
    }


}
