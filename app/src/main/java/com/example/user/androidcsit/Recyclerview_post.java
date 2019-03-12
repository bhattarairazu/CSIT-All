package com.example.user.androidcsit;

import android.content.Context;
import android.content.Intent;
import android.media.TimedText;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.androidcsit.Activity.DetailsPost_View;
import com.example.user.androidcsit.Model_ddatta;
import com.example.user.androidcsit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recyclerview_post extends RecyclerView.Adapter<Recyclerview_post.MyViewHolder> {
    private Context mcontext;
    private List<Model_ddatta> mgetdata;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    boolean datas;

    public Recyclerview_post(Context mcontext) {
        this.mcontext = mcontext;
    }

    public Recyclerview_post(Context mcontext, List<Model_ddatta> mgetdata, boolean datas) {
        this.mcontext = mcontext;
        this.mgetdata = mgetdata;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = null;
        if(datas ==  true){
            mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_eventsforum,parent,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        }else{
            mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.emptyrecyclerview,parent,false);
        }
        return new MyViewHolder(mview);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Model_ddatta mdatas = mgetdata.get(position);
        final String blogid = mgetdata.get(position).Postid;
        final String currntuid  =firebaseAuth.getCurrentUser().getUid();
        String desc =mgetdata.get(position).getPostdetail();
        holder.setDescText(desc);

        String image = mgetdata.get(position).getProfileimage();
        String names = mgetdata.get(position).getUsername();
        holder.setUserData(names,image);

        try {
            long millisecond = mgetdata.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            holder.setTime(dateString);
        } catch (Exception e) {

//            Toast.makeText(mcontext, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
//        holder.mtextviewe_title.setText(mdatas.getUsername());
//        holder.mtextview_tag.setText("Forum");
//        holder.mtextview_descripion.setText(mdatas.getPostdetail());
//        holder.mdate.setText(mdatas.getTimestamp());
        //Get Likes Count
        firebaseFirestore.collection("Post/" + blogid + "/Likes").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();

                    holder.updateLikesCount(count);

                } else {

                    holder.updateLikesCount(0);

                }

            }
        });
        //Get Likes
        firebaseFirestore.collection("Post/" + blogid + "/Likes").document(currntuid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if(documentSnapshot.exists()){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.bloglike.setImageDrawable(mcontext.getDrawable(R.drawable.like));
                    }

                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.bloglike.setImageDrawable(mcontext.getDrawable(R.drawable.action_like_gray));
                    }

                }

            }
        });

        //gettinglikes
        holder.bloglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Post/" + blogid + "/Likes").document(currntuid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Post/" + blogid + "/Likes").document(currntuid).set(likesMap);

                        } else {

                            firebaseFirestore.collection("Post/" + blogid + "/Likes").document(currntuid).delete();

                        }

                    }
                });
            }
        });
            holder.blogcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newinten = new Intent(v.getContext(), DetailsPost_View.class);
                    newinten.putExtra("blogid",blogid);
                   v.getContext().startActivity(newinten);
                }
            });

    }

    @Override
    public int getItemCount() {
    return mgetdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mtextviewe_title,mtextview_tag,mtextview_descripion,mdate,bloglikecount,blogcommentcount;
        CircleImageView mimaaggeview;
        ImageView bloglike,blogcomment;
        private View mview;
        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
//            mtextview_descripion = (TextView) itemView.findViewById(R.id.mtextvuew_post);
//            mtextview_tag =(TextView) itemView.findViewById(R.id.tetview_tag);
//            mtextviewe_title=(TextView) itemView.findViewById(R.id.mtexxtview_titles);
//            mdate = (TextView) itemView.findViewById(R.id.mtextxvew_date);
//            mtextview_descripion.setOnClickListener(this);
            bloglike =(ImageView) mview.findViewById(R.id.mimage_likebefore);
            blogcomment =(ImageView) mview.findViewById(R.id.mimage_commenteafter);

//        }
//
//        @Override
//        public void onClick(View v) {
//            Intent newinten = new Intent(v.getContext(), DetailsPost_View.class);
//            v.getContext().startActivity(newinten);

        }
        public void setDescText(String descText){

            mtextview_descripion = mview.findViewById(R.id.mtextvuew_post);
            mtextview_descripion.setText(descText);

        }
        public void setTime(String date) {

            mdate  = mview.findViewById(R.id.mtextxvew_date);
           mdate.setText(date);

        }
        public void setUserData(String name,String image){

            mimaaggeview = mview.findViewById(R.id.mcricular_image_profille);
            mtextviewe_title = mview.findViewById(R.id.mtexxtview_titlesevents);

            mtextviewe_title.setText(name);

//            RequestOptions placeholderOption = new RequestOptions();
//            placeholderOption.placeholder(R.drawable.profile_placeholder);
//
//            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(blogUserImage);
            Picasso.get().load(image).into(mimaaggeview);

        }
        public void updateLikesCount(int count){

            bloglikecount = mview.findViewById(R.id.bloglikes_count);
            bloglikecount.setText(count + " Likes");

        }
    }

}