package com.example.user.androidcsit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.androidcsit.Model.Model_commentdata;
import com.example.user.androidcsit.Model.Model_questions;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/29/2018.
 */

public class CustomDownloadAdapter extends BaseAdapter {
    private List<Model_questions> mlist = new ArrayList<>();
    private Context mcontext;

    public CustomDownloadAdapter(List<Model_questions> mlist, Context mcontext) {
        this.mlist = mlist;
        this.mcontext = mcontext;
    }

    public void add(Model_questions mcoment) {
        this.mlist.add(mcoment);
        notifyDataSetChanged(); // to render the list we need to notify
    }
    @Override
    public int getCount() {
      return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewViewHolder holder = new NewViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = messageInflater.inflate(R.layout.cardview_filedownload, null);
       Model_questions coment_data = mlist.get(position);
        holder.mtextview =(TextView) convertView.findViewById(R.id.mtextview_qncg);
        holder.mtextview.setText(coment_data.getName());

        return convertView;
    }


    private class NewViewHolder {
        TextView  mtextview;
    }
}
