package com.example.user.androidcsit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.user.androidcsit.Activity.Detailsnoticeread;
import com.example.user.androidcsit.Model.Model_notice;
import com.example.user.androidcsit.R;

import java.util.List;

/**
 * Created by User on 8/30/2018.
 */

public class Recyclerview_noticeAdapter extends RecyclerView.Adapter<Recyclerview_noticeAdapter.MyViewHolder> {
    private Context mmconntext;
    private List<Model_notice> mlist;

    public Recyclerview_noticeAdapter(Context mmconntext, List<Model_notice> mlist) {
        this.mmconntext = mmconntext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_singlenotice,parent,false);
        return new MyViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Model_notice mmnnotice  = mlist.get(position);
    holder.mdes.setText(mmnnotice.getDescription());
    holder.mtitle.setText(mmnnotice.getTitle());
    holder.mmdate.setText(mmnnotice.getDatte());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mtitle,mmdate,mdes;
        public MyViewHolder(View itemView) {
            super(itemView);
            mtitle =(TextView) itemView.findViewById(R.id.mtextview_noticetitle);
            mmdate = (TextView) itemView.findViewById(R.id.mtextvieew_nnoticedate);
            mdes =(TextView) itemView.findViewById(R.id.mttextieww_noticedeess);
            mtitle.setOnClickListener(this);
            mdes.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext(), Detailsnoticeread.class));

        }
    }
}
