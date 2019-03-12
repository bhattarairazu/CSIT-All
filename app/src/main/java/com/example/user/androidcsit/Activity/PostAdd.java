package com.example.user.androidcsit.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 9/12/2018.
 */

public class PostAdd extends AppCompatActivity{
    private EditText meditet_post;
    private Button mbtn;
    private ImageView mimageview;
    private Uri postImageUri = null;

    private ProgressBar newPostProgress;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id,name,image;

    private Bitmap compressedImageFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd);
//        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser  muser = firebaseAuth.getCurrentUser();
        if(muser!=null){
            name = muser.getDisplayName();
            image = muser.getPhotoUrl().toString();
            current_user_id = muser.getUid();

        }

//        current_user_id = firebaseAuth.getCurrentUser().getUid();
        newPostProgress = findViewById(R.id.new_post_progress);
        mbtn = (Button) findViewById(R.id.post_btn);
        meditet_post = (EditText) findViewById(R.id.new_post_desc);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 3;
                String post = meditet_post.getText().toString();
                Map<String, Object> userdate = new HashMap<>();
                userdate.put("Postdetail", post);
                userdate.put("userid", current_user_id);
                userdate.put("username",name);
                userdate.put("timestamp", FieldValue.serverTimestamp());
                userdate.put("profileimage",image);
                firebaseFirestore.collection("Post").add(userdate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PostAdd.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostAdd.this, "fAILED"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
//        mimageview =(ImageView) findViewById(R.id.new_post_image);
//        mimageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                CropImage.activity()
////                        .setGuidelines(CropImageView.Guidelines.ON)
////                        .setMinCropResultSize(512, 512)
////                        .setAspectRatio(1, 1)
////                        .start(NewPostActivity.this);
//            }
//        });
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                postImageUri = result.getUri();
//                mimageview.setImageURI(postImageUri);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//
//                Exception error = result.getError();
//
//            }
//        }
//
//    }
    }
}
