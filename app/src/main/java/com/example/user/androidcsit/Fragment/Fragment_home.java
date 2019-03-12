package com.example.user.androidcsit.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import com.example.user.androidcsit.Activity.Editprofile;
import com.example.user.androidcsit.Activity.PostAdd;
import com.example.user.androidcsit.Activity.Profile;
import com.example.user.androidcsit.Model_ddatta;
import com.example.user.androidcsit.R;
import com.example.user.androidcsit.Recyclerview_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 8/18/2018.
 */

public class Fragment_home extends Fragment {
    private RecyclerView mrecyclerview;
    private List<Model_ddatta> marraylist = new ArrayList<>();
    private RecyclerView.LayoutManager mlayoutmanager;
    private Recyclerview_post madapter;
    private String title[]={"Texas","MadanBhandar","Orchid","Hari khatiwada","Youtube"};
    private String post[]={"Texas is verbad college.it is the worst college at all.","MadanBhandar is one of the best colleg in k town.it has different best facilities","Orchid","This all answer may be right, and work very fine, I use this options for few days and it's work for me too, but there are one other option and i think which is much better because, this option is ","This all answer may be right, and work very fine, I use this options for few days and it's work for me too, but there are one other option and i think which is much better because, this option is "};
    private String tag[]={"News","Events","Forum","Events","News"};
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ImageView mimage,mimagehome;
    private ActionBarDrawerToggle drawerToggle;
    private ListView mlistview;
    private ArrayAdapter<String> maadapter;
    private FloatingActionButton mbtn;
    private static int mCount = 5;
    NotificationBadge badge;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;
    FirebaseUser muser;
    String image,name;
    private CircleImageView mimageview;
    private TextView mtextvieww,mlogout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View mview = inflater.inflate(R.layout.fragment_home, container, false);


//        image = getArguments().getString("imagaes");
//        name  = getArguments().getString("name");

        mimageview =(CircleImageView) mview.findViewById(R.id.profile_image);
        mtextvieww =(TextView) mview.findViewById(R.id.profile_name);
        mlogout =(TextView) mview.findViewById(R.id.mtextview_logout);
        mrecyclerview = (RecyclerView) mview.findViewById(R.id.mrrecycler_view);
        mimage = (ImageView) mview.findViewById(R.id.mimagevieew_notification);
        mimagehome = (ImageView) mview.findViewById(R.id.mimageview_menu);
        final RippleView rippleView = (RippleView) mview.findViewById(R.id.ripple_notify);
        mDrawer = (DrawerLayout) mview.findViewById(R.id.drawer_layout);
        mlistview = (ListView) mview.findViewById(R.id.mlisstview);
        badge = (NotificationBadge) mview.findViewById(R.id.badge);
        badge.setNumber(mCount++);
        mbtn = (FloatingActionButton) mview.findViewById(R.id.add_post_btn);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mview.getContext(), PostAdd.class));
            }
        });

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mDrawer.openDrawer(GravityCompat.END);
            }
        });
        mimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mimagehome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });

//        for(int i = 0;i<title.length;i++){
//            Model_ddatta mdata = new Model_ddatta(title[i],post[i],tag[i]);
//            marraylist.add(mdata);
//        }

        mlayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        mrecyclerview.setLayoutManager(mlayoutmanager);
        madapter = new Recyclerview_post(getContext(), marraylist,true);
        mrecyclerview.setItemAnimator(new DefaultItemAnimator());
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setAdapter(madapter);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser useerr = firebaseAuth.getCurrentUser();
//        if(useerr!=null){
//
//        }
        if(firebaseAuth.getCurrentUser() != null) {
            name = useerr.getDisplayName();
            image = useerr.getPhotoUrl().toString();
            mtextvieww.setText(name);
            Picasso.get().load(image).into(mimageview);
            firebaseFirestore = FirebaseFirestore.getInstance();

            mrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if(reachedBottom){

                        loadMorePost();

                    }

                }
            });
        }
        Query firstquer = firebaseFirestore.collection("Post").orderBy("timestamp",Query.Direction.DESCENDING);
        firstquer.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        String blogid = doc.getDocument().getId();
                        Model_ddatta mdatsa = doc.getDocument().toObject(Model_ddatta.class).withId(blogid);
                        marraylist.add(mdatsa);
                        madapter.notifyDataSetChanged();
                    }
                }
            }
        });


//firebase


            //endfirebase
            mlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                }
            });
            return mview;

    }

//
//    public void listdata(){
//        final ArrayList<String> list = new ArrayList<>();
//        for(int i = 0;i<title.length;i++){
//           // Model_ddatta mdata = new Model_ddatta(title[i],post[i],tag[i]);
//           list.add(title[i]);
//        }
//        maadapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_list_item_1,list);
//        mlistview.setAdapter(maadapter);
//
//    }

    @Override
    public void onStart() {
        super.onStart();
        muser = FirebaseAuth.getInstance().getCurrentUser();
        if(muser==null){


//                //Log.d("error", "onComplete: sposstmap"+postMap);
//                firebaseFirestore.collection("UsersDetails/"+muser.getUid()+"/ProfileDetails").document(muser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(!task.getResult().exists()){
//                            Map<String, Object> postMap = new HashMap<>();
//                            postMap.put("username", "");
//                            postMap.put("userprofileimage", "");
//                            postMap.put("useraddress", "");
//                            postMap.put("userstatus", "");
//                            postMap.put("usercoverfoto","");
//                            postMap.put("userfollowers",0);
//                            postMap.put("usersfollowing",0);
//                            postMap.put("userspost",0);
//                            postMap.put("userid","");
//                            firebaseFirestore.collection("UsersDetails/"+muser.getUid()+"/ProfileDetails").document(muser.getUid()).set(postMap);
//                        }
//                    }
//                });


        }
    }

    public void loadMorePost(){

    if(firebaseAuth.getCurrentUser() != null) {

        Query nextQuery = firebaseFirestore.collection("Post")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(lastVisible)
                .limit(3);

        nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                    for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String blogid = doc.getDocument().getId();
                            Model_ddatta mdatsa = doc.getDocument().toObject(Model_ddatta.class).withId(blogid);
                            marraylist.add(mdatsa);
                            madapter.notifyDataSetChanged();
                        }
                    }
                }

            }
        });

    }

    }
}
