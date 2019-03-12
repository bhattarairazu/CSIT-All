package com.example.user.androidcsit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.user.androidcsit.R;


import java.util.List;

/**
 * Created by User on 8/29/2018.
 */

public class Listview_randomcolor extends ArrayAdapter<String> {
    private List<String> friendList;
    private Context mconntext;

    public Listview_randomcolor(@NonNull Context context, int resource,List<String> objects) {
        super(context, resource, objects);
        this.mconntext = context;
        this.friendList = objects;
    }


    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public String getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ImageHolder holder;
        LayoutInflater inflater = (LayoutInflater) mconntext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
// If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.lisstview_singlesemesterr, parent, false);
            // get all UI view
            holder = new ImageHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ImageHolder) convertView.getTag();
        }
        holder.friendName.setText(getItem(position));
        //get first letter of each String item
        String firstLetter = String.valueOf(getItem(position).charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px
        holder.imageView.setImageDrawable(drawable);
        return convertView;
    }


    private class ImageHolder {
        private ImageView imageView;
        private TextView friendName;
        public ImageHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.image_view);
            friendName = (TextView) convertView.findViewById(R.id.gmailitem_title);

        }
    }
}
