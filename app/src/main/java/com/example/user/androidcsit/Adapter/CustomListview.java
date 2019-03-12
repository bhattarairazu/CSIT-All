package com.example.user.androidcsit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.androidcsit.Model.Model_commentdata;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 8/28/2018.
 */

public class CustomListview extends BaseAdapter {
    private List<Model_commentdata> mlist = new ArrayList<>();
    private Context mcontext;

    public CustomListview( Context mcontext) {
        this.mcontext = mcontext;
    }
    public void add(Model_commentdata mcoment) {
        this.mlist.add(mcoment);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        if(mlist!=null){
            return mlist.size();

        }else {
            return 0;
        }
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
        CommentViewHolder holder = new CommentViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = messageInflater.inflate(R.layout.cardview_singlecomment, null);
        Model_commentdata coment_data = mlist.get(position);
        holder.mimageview_profile = (ImageView) convertView.findViewById(R.id.mcricular_image);
        holder.mtextview_name  = (TextView) convertView.findViewById(R.id.mtexxtview_titles);
        holder.textview_date = (TextView) convertView.findViewById(R.id.textview_dates);
        holder.textview_title = (TextView) convertView.findViewById(R.id.mtextview_tit);

        holder.textview_title.setText(coment_data.getComments());
        try {
            long millisecond = coment_data.getTimestamp().getTime();
            String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
            holder.textview_date.setText(dateString);
        } catch (Exception e) {

//            Toast.makeText(mcontext, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        holder.mtextview_name.setText(coment_data.getUsername());

        return convertView;
    }

    private class CommentViewHolder {
        ImageView mimageview_profile;
        TextView mtextview_name,textview_title,textview_date;
    }
}
