package com.example.user.androidcsit.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.androidcsit.R;


/**
 * Created by User on 8/18/2018.
 */

public class Settings extends Fragment {
    private ImageView mimageviewe;
    private TextView mtextxview,settings,noteice,profiletext;
    private Button mbtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View mview = inflater.inflate(R.layout.activity_settings,container,false);
        mimageviewe =(ImageView) mview.findViewById(R.id.mimageview_back);
        mtextxview =(TextView) mview.findViewById(R.id.textview_title);
        mtextxview.setText("Setting");
        settings=(TextView) mview.findViewById(R.id.mtetview_settings);
        noteice =(TextView) mview.findViewById(R.id.mtexxtview_notice);
        profiletext =(TextView) mview.findViewById(R.id.mtetxtview_profile);
        profiletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Profile.class));
            }
        });
        noteice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Notice.class));
            }
        });

       settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Settingspreferences.class));
            }
        });
        return mview;
    }
}
