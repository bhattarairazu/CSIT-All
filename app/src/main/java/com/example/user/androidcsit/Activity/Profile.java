package com.example.user.androidcsit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.androidcsit.Model.Model_questions;
import com.example.user.androidcsit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 9/22/2018.
 */

public class Profile extends AppCompatActivity {
    FirebaseUser mfirebaseuser;
    FirebaseAuth mauth;
    FirebaseFirestore firebaseFirestore;
    String name,imgurl,currentuid;
    String status,address,postno,followers,following,uid,coverfoto,names,fotos_profille;
    ImageView mimageview_profie,imagve_cover;
    TextView textview_editprofile,textview_name,textview_address,textviw_status,textview_followrs,textview_followin,texview_posts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mauth = FirebaseAuth.getInstance();
        mfirebaseuser = mauth.getCurrentUser();
        if(mfirebaseuser!=null){
            name = mfirebaseuser.getDisplayName();
            imgurl = mfirebaseuser.getPhotoUrl().toString();
            currentuid = mfirebaseuser.getUid();
        }
        textview_name =(TextView) findViewById(R.id.mtextview_profilenames);
        textview_name.setText(name);
        mimageview_profie =(ImageView)findViewById(R.id.image_profile);
        imagve_cover =(ImageView)findViewById(R.id.image_cover);
        textview_address = (TextView) findViewById(R.id.mtextviewe_profiletext);
        textviw_status =(TextView) findViewById(R.id.mtextview_status);
        textview_followin =(TextView) findViewById(R.id.mtextview_profile_following);
        textview_followrs =(TextView) findViewById(R.id.mtextview_profile_followers);
        texview_posts =(TextView) findViewById(R.id.mtextview_profile_postno);
        Picasso.get().load(imgurl).into(mimageview_profie);

        textview_editprofile =(TextView) findViewById(R.id.mtextview_editprofile);
        textview_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Profile.this, Editprofile.class));
                Bundle bundle = new Bundle();
                bundle.putString("userstatus", status);
                bundle.putString("userpost",postno);
                bundle.putString("useraddress",address);
                bundle.putString("userfollowers",followers);
                bundle.putString("usersfollowing",following);
                bundle.putString("userid",uid);
                bundle.putString("usercoverfoto",coverfoto);
                bundle.putString("userprofile",fotos_profille);
                bundle.putString("username",names);
                Intent i = new Intent(Profile.this,Editprofile.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("UsersDetails/"+currentuid+"/ProfileDetails").document(currentuid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                   names = documentSnapshot.getString("username");
                   fotos_profille = documentSnapshot.getString("userprofileimage");
                   status = documentSnapshot.getString("userstatus");
                   postno = documentSnapshot.getString("userspost");
                    followers = documentSnapshot.getString("userfollowers");
                     following = documentSnapshot.getString("usersfollowing");
                  uid = documentSnapshot.getString("userid");
                     coverfoto = documentSnapshot.getString("usercoverfoto");
                     address = documentSnapshot.getString("useraddress");

                                    Picasso.get().load(coverfoto).into(imagve_cover);
                                    textviw_status.setText(status);
                                    textview_address.setText(address);
                                    textview_followin.setText(following);
                                    textview_followrs.setText(followers);
                                    texview_posts.setText(postno);
//                    Log.d("prof", "onEvent: ss"+name+status);
                }

                }



        });



}
}
