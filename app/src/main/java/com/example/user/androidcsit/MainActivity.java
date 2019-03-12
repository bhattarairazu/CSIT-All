package com.example.user.androidcsit;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.androidcsit.Activity.Files;
import com.example.user.androidcsit.Activity.Jobs;
import com.example.user.androidcsit.Activity.Settings;
import com.example.user.androidcsit.Fragment.Fragment_home;
import com.example.user.androidcsit.Fragment.Fragment_routine;
import com.example.user.androidcsit.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout layout_home,layout_jobs,layout_setting,layout_files,layout_section;
    private ImageView mimage_home,mimage_jobs,mimage_settings,mimage_files,mimage_section;
    private TextView mtext_home,mtext_jobs,mtext_setings,mtext_files,mtext_secion;
    String images,name;
    private static String TAG ="MainActivity";
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
      images = getIntent().getExtras().getString("images");
      name = getIntent().getExtras().getString("name");
        Log.d(TAG, "onCreate: jjjjj"+images+name);
        fab =(FloatingActionButton) findViewById(R.id.home_bn);
        layout_home =(LinearLayout) findViewById(R.id.mlinearlayout_home);

        layout_jobs =(LinearLayout) findViewById(R.id.mlinearlayout_jobs);
        mimage_jobs = (ImageView) findViewById(R.id.mimageView_jobs);

        layout_setting =(LinearLayout) findViewById(R.id.mlinearlayout_settings);
        mimage_settings = (ImageView) findViewById(R.id.mimageView_settings);

        layout_files =(LinearLayout) findViewById(R.id.mlinearlayout_files);
        mimage_files = (ImageView) findViewById(R.id.mimageView_files);

        layout_section =(LinearLayout) findViewById(R.id.mlinearlayout_section);
        mimage_section = (ImageView) findViewById(R.id.mimageView_section);
//
        layout_home.setOnClickListener(this);
       fab.setOnClickListener(this);

        layout_jobs.setOnClickListener(this);
        mimage_jobs.setOnClickListener(this);

        layout_setting.setOnClickListener(this);
        mimage_settings.setOnClickListener(this);

        layout_files.setOnClickListener(this);
        mimage_files.setOnClickListener(this);

        layout_section.setOnClickListener(this);
        mimage_section.setOnClickListener(this);
        Bundle mbundle = new Bundle();
        mbundle.putString("names",name);
        mbundle.putString("images",images);
//        loadFragment(new Fragment_home()).setArguments(mbundle);
        Fragment_home mhome = new Fragment_home();
        mhome.setArguments(mbundle);
        loadFragment(mhome);
//
//
//        for(int i = 0;i<title.length;i++){
//            Model_ddatta mdata = new Model_ddatta(title[i],post[i],tag[i]);
//            marraylist.add(mdata);
//        }
//        mlayoutmanager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
//        mrecyclerview.setLayoutManager(mlayoutmanager);
//        madapter = new Recyclerview_post(MainActivity.this,marraylist);
//        mrecyclerview.setAdapter(madapter);




    }

    @Override
    public void onClick(View v) {

        //creating fragment object
        Fragment fragment = null;
        int id = v.getId();
        switch (id) {
            case R.id.mlinearlayout_home:
            case R.id.home_bn:
                fragment = new Fragment_home();



                break;
            case R.id.mlinearlayout_section:
            case R.id.mimageView_section:
                mimage_section.setImageResource(R.drawable.ic_blue_home);

               fragment = new Fragment_routine();
                break;
            case R.id.mlinearlayout_files:
            case R.id.mimageView_files:
                fragment = new Files();
                break;
            case R.id.mlinearlayout_jobs:
            case R.id.mimageView_jobs:
              fragment = new Jobs();
                break;
            case R.id.mlinearlayout_settings:
            case R.id.mimageView_settings:
                Toast.makeText(this, "sdfsdfsdfsdffsdf", Toast.LENGTH_SHORT).show();
                fragment = new Settings();

                break;
            default:
        }
        loadFragment(fragment);


    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mfragments, fragment)
//                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}
