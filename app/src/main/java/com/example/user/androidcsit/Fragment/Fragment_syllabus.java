package com.example.user.androidcsit.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.user.androidcsit.Activity.DetailsFilesActivity;
import com.example.user.androidcsit.Adapter.Recyclerview_questionAdapter;
import com.example.user.androidcsit.Model.Model_questions;
import com.example.user.androidcsit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Razu on 12/1/2017.
 */

public class Fragment_syllabus extends Fragment {

    private ListView mlisview;
   List<Model_questions> marrya = new ArrayList<>();
    private static final String[] subject = {
            "Micro Processor",
            "Digital Logic",
            "Theory Of Computing",
            "Cognitive Science",
            "Database",
            "Technical Writing",
            "System Analysis And Design",
            "Computer Graphics",
            "Object Oriented Programming",
            "Principle Of Management",
            "Numerical Method",
            "Discrete Structure",
            "C",
            "Data Structure And Algorithm",
            "Computer Architecture"
    };
    List<String> mlist = new ArrayList<>();
    private FirebaseFirestore mfirestore;
    private FirebaseAuth mauth;
    public Fragment_syllabus() {
    }

    private EditText meditetext;
    ArrayAdapter<String> madapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syllabus, container, false);
        mauth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();

        FirebaseUser muser = mauth.getCurrentUser();
        if(muser!=null){

        }
        mlisview =(ListView) view.findViewById(R.id.mlistview_syllabus);
        meditetext = (EditText) view.findViewById(R.id.medittext_search);
        for(int i = 0;i<subject.length;i++){
            mlist.add(subject[i]);
        }
         madapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mlist);
        mlisview.setAdapter(madapter);
        meditetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                madapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mlisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Intent minten = new Intent(view.getContext(), DetailsFilesActivity.class);

                minten.putExtra("name",name);
                minten.putExtra("type","Syllabus");
                startActivity(minten);


            }
        });
//       mfirestore.collection("Question")
//                .whereEqualTo("Fourth Semester", true)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d("fragment", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d("fragment", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });


        return view;
    }
    //checking internet connection
//    public boolean isconnectd(){
//        ConnectivityManager mconn = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo minfo =mconn.getActiveNetworkInfo();
//        if(minfo != null && minfo.isConnected()){
//            return true;
//        }
//        return false;
//    }   }
}

