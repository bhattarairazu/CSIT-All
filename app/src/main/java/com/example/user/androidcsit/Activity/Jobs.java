package com.example.user.androidcsit.Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.androidcsit.Adapter.Recyclerview_jobsadapter;
import com.example.user.androidcsit.Model.Model_jobs;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/18/2018.
 */

public class Jobs extends Fragment {
    private ImageView mimageviewe;
    private TextView mtextxview;
    private RecyclerView mrecyclerview;
    private RecyclerView.LayoutManager mlayout;
    private Recyclerview_jobsadapter madapter;

    private String[] jobsname ={"Android Developer","Web Developer","Graphics Designer","Drivere","System Analyst"};
    private String[] jobscompany ={"Acepirit Inc","F1 Soft","Khalti","e-Sewa","Versend"};
    private String[] jobslocation ={"New Baneshwork","Shankhamul","Kamaladi","kupandole","chabahil"};
    private String[] jobsposition ={"Mid level","Senior level","Beginner","Intermediate","Intern"};
    private String[] jobsdeadline = {"Deadline : 4 weeks from now","Deadline:2 days from now","Deadline:1 day from now","Deadline 5 hrs from now","Deadline: 6 weeks from now"};
    private boolean[] boolens = {true,false,true,false,true};
    private List<Model_jobs> mlist = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.activity_jobs,container,false);
        mimageviewe =(ImageView) mview.findViewById(R.id.mimageview_back);
        mtextxview =(TextView) mview.findViewById(R.id.textview_title);
        mtextxview.setText("Jobs");
        mrecyclerview=(RecyclerView) mview.findViewById(R.id.mrecyclerview_jobs);
        mlayout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mrecyclerview.setLayoutManager(mlayout);
        for (int i = 0;i<jobsname.length;i++){
            Model_jobs mmodel = new Model_jobs(jobsname[i],jobslocation[i],jobsposition[i],jobsdeadline[i],jobscompany[i],boolens[i]);
            mlist.add(mmodel);

        }
        mrecyclerview.setAdapter(new Recyclerview_jobsadapter(mlist,getContext()));


        return mview;
    }
}
