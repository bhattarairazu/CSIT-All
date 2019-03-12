package com.example.user.androidcsit.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.androidcsit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

/**
 * Created by User on 9/22/2018.
 */

public class Editprofile extends AppCompatActivity {
    EditText medit_status,medit_addreess,medit_profileimage;
    ImageView mimageiew;
    Button btn_update,btn_cancel;
    FirebaseAuth mauth;
    private Uri postImageUri = null;
    FirebaseUser muser;
    private String current_user_id;
    String name,url;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    String status,address,postno,followers,following,uid,coverfoto,names,fotos_profille,userid;
    private Bitmap compressedImageFile;
    private ProgressBar newPostProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mimageiew =(ImageView) findViewById(R.id.mimagevie_eidt_cover);
        medit_addreess =(EditText) findViewById(R.id.medittext_edit_address);
        medit_status =(EditText) findViewById(R.id.medittext_edit_status);
        newPostProgress = findViewById(R.id.new_post_progress);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.isEmpty()){
            status = bundle.getString("userstatus");
            postno =bundle.getString("userpost");
            address = bundle.getString("useraddress");
            followers=bundle.getString("userfollowers");
            following=bundle.getString("usersfollowing");
            userid = bundle.getString("userid");
            coverfoto = bundle.getString("usercoverfoto");
            fotos_profille= bundle.getString("userprofile");
            names= bundle.getString("username");
//            mimageiew.setVisibility(View.VISIBLE);
            medit_addreess.setText(address);
            medit_status.setText(status);

        }else{
            postno = "0";
            followers = "0";
            following = "0";
        }

        if(muser!=null){
            name =muser.getDisplayName();
            url = muser.getPhotoUrl().toString();
            current_user_id = muser.getUid();

        }


        medit_profileimage =(EditText) findViewById(R.id.medittext_edit_upoadcover);
        btn_cancel =(Button) findViewById(R.id.mbt_cancel);
        btn_update=(Button) findViewById(R.id.mbt_update);

        //btn click listener
        medit_profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(Editprofile.this);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String status = medit_status.getText().toString();
            final String address = medit_addreess.getText().toString();
            if(!TextUtils.isEmpty(status) && !TextUtils.isEmpty(address)){
                final String randomName = UUID.randomUUID().toString();

                newPostProgress.setVisibility(View.VISIBLE);
                // PHOTO UPLOAD
                File newImageFile = new File(postImageUri.getPath());
                try {

                    compressedImageFile = new Compressor(Editprofile.this)
                            .setMaxHeight(720)
                            .setMaxWidth(720)
                            .setQuality(50)
                            .compressToBitmap(newImageFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();

                // PHOTO UPLOAD
                Log.d("error", "onClick: jjj");
                UploadTask filePath = storageReference.child("user_coverphoto").child(randomName + ".jpg").putBytes(imageData);
                filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        final String downloadUri = task.getResult().getDownloadUrl().toString();
                        if(task.isSuccessful()){
                        File newThumbFile = new File(postImageUri.getPath());
                        try {

                            compressedImageFile = new Compressor(Editprofile.this)
                                    .setMaxHeight(100)
                                    .setMaxWidth(100)
                                    .setQuality(1)
                                    .compressToBitmap(newThumbFile);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] thumbData = baos.toByteArray();
                            Log.d("error", "onComplete: skskskkssss"+thumbData);
                            UploadTask uploadTask = storageReference.child("user_coverphoto/cover")
                                    .child(randomName + ".jpg").putBytes(thumbData);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String downloadthumbUri = taskSnapshot.getDownloadUrl().toString();

                                    firebaseFirestore.collection("UsersDetails/"+current_user_id+"/ProfileDetails").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.getResult().exists()){

                                                Map<String, Object> postMap = new HashMap<>();
                                                postMap.put("username", names);
                                                postMap.put("userprofileimage", fotos_profille);
                                                postMap.put("useraddress", address);
                                                postMap.put("userstatus", status);
                                                postMap.put("usercoverfoto",downloadUri);
                                                postMap.put("userfollowers",followers);
                                                postMap.put("usersfollowing",following);
                                                postMap.put("userspost",postno);
                                                postMap.put("userid",userid);
                                                Log.d("error", "onComplete: sposstmap"+postMap);
                                                firebaseFirestore.collection("UsersDetails/"+current_user_id+"/ProfileDetails").document(current_user_id).set(postMap);
                                            }
                                            newPostProgress.setVisibility(View.VISIBLE);

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Editprofile.this, "failed"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

            }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                medit_profileimage.setVisibility(View.GONE);
                mimageiew.setVisibility(View.VISIBLE);

                postImageUri = result.getUri();
                mimageiew.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }
}
