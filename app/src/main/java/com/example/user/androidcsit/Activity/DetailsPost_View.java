package com.example.user.androidcsit.Activity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.androidcsit.Adapter.CustomListview;
import com.example.user.androidcsit.Model.Model_commentdata;
import com.example.user.androidcsit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 8/28/2018.
 */

public class DetailsPost_View extends AppCompatActivity {
    private EditText meditext;
    private ImageView mimage;
    ListView mlistview;
    FirebaseAuth  firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CustomListview mcustomAdapter;
    String name,current_user_id,image,postids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspostview);
        meditext = (EditText) findViewById(R.id.medittext1);
        mcustomAdapter = new CustomListview(getApplicationContext());
        mimage = (ImageView) findViewById(R.id.mmvimageview_send);
        mlistview =(ListView) findViewById(R.id.mlistview_detailspost);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser muser = firebaseAuth.getCurrentUser();
        postids = getIntent().getExtras().getString("blogid");
        if(muser!=null){
            name = muser.getDisplayName();
            image = muser.getPhotoUrl().toString();
            current_user_id = muser.getUid();

        }

        firebaseFirestore.collection("Post/"+postids+"/Comments").addSnapshotListener(DetailsPost_View.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(!documentSnapshots.isEmpty()){
                    for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                                String commentid = doc.getDocument().getId();
                                Model_commentdata mcomment = doc.getDocument().toObject(Model_commentdata.class);
                                mcustomAdapter.add(mcomment);
                                mlistview.setAdapter(mcustomAdapter);
                        }
                    }
                }
            }
        });

        mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String texts = meditext.getText().toString();
//                Model_commentdata mcommentdata = new Model_commentdata("Bhattarai","2 hrs ago",1,"imges",text);
                Map<String, Object> comment = new HashMap<>();

                comment.put("userid", current_user_id);
                comment.put("username",name);
                comment.put("comments",texts);
                comment.put("timestamp", FieldValue.serverTimestamp());
                comment.put("profileimage",image);
                firebaseFirestore.collection("Post/"+postids+"/Comments").add(comment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(!task.isSuccessful()){

                            Toast.makeText(DetailsPost_View.this, "Error Posting Comment : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {

                            meditext.setText("");

                        }
                    }
                });
            }
        });


    }
}
