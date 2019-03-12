package com.example.user.androidcsit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.user.androidcsit.Activity.Detail_jobs;
import com.example.user.androidcsit.Model.Model_jobs;
import com.example.user.androidcsit.R;

import java.util.List;

/**
 * Created by User on 9/22/2018.
 */

public class Recyclerview_jobsadapter extends RecyclerView.Adapter<Recyclerview_jobsadapter.MyViewHoldere> {
    private List<Model_jobs> mjobs;
    private Context mcontext;

    public Recyclerview_jobsadapter(List<Model_jobs> mjobs, Context mcontext) {
        this.mjobs = mjobs;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHoldere onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mvie = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_jobsinle,parent,false);
        return new MyViewHoldere(mvie);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldere holder, int position) {
        Model_jobs mgetdata = mjobs.get(position);
        holder.mtextview_title.setText(mgetdata.getJobs_title());
        holder.mtexxtview_company.setText(mgetdata.getCompany());
        holder.mtextview_locatitoin.setText(mgetdata.getLocation());
        holder.mtextview_dedlines.setText(mgetdata.getDeadline());
        holder.mtextxvivew_posstion.setText(mgetdata.getPosition());
        if(mgetdata.isFavourite()){
            holder.mimmag_favourite.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_favouriteselected));
        }

    }

    @Override
    public int getItemCount() {
        return mjobs.size();
    }

    public class MyViewHoldere extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView mimageview_compimage,mimmag_favourite;
        RippleView mripplevew;
        TextView mtextview_title,mtextxvivew_posstion,mtexxtview_company,mtextview_locatitoin,mtextview_dedlines;
        public MyViewHoldere(final View itemView) {
            super(itemView);
            mimageview_compimage =(ImageView) itemView.findViewById(R.id.mimageview_jobsimaage);
            mimmag_favourite =(ImageView) itemView.findViewById(R.id.mmimageview_jobsfavouirte);
            mtextview_title =(TextView) itemView.findViewById(R.id.mtextview_jjobstitle);
            mtextview_dedlines =(TextView) itemView.findViewById(R.id.mtextview_jobsdeadline);
            mtextxvivew_posstion =(TextView) itemView.findViewById(R.id.mtextview_jobslevel);
            mtextview_locatitoin =(TextView) itemView.findViewById(R.id.mtextview_jobslocation);
            mtexxtview_company =(TextView) itemView.findViewById(R.id.mtextview_jobscompany);

            mripplevew =(RippleView) itemView.findViewById(R.id.mmripplevieew_jobs);
            mripplevew.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    //Toast.makeText(mcontext, "sdfsdfsdfsdfsdfsdfsdf", Toast.LENGTH_SHORT).show();
                    Intent mintent = new Intent(mcontext, Detail_jobs.class);
                    mcontext.startActivity(mintent);
                }
            });
            mimmag_favourite.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.mmripplevieew_jobs:
                    Intent mintent = new Intent(v.getContext(), Detail_jobs.class);
                    mcontext.startActivity(mintent);
                    break;
                case R.id.mmimageview_jobsfavouirte:
//                    mimmag_favourite.setImageDrawable(v.getResources().getDrawable(R.drawable.ic_favouriteselected));
            }

        }
    }
}
