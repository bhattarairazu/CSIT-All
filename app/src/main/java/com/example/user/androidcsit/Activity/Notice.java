package com.example.user.androidcsit.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.example.user.androidcsit.Adapter.Recyclerview_noticeAdapter;
import com.example.user.androidcsit.Model.Model_notice;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/30/2018.
 */

public class Notice extends AppCompatActivity {
    RecyclerView mrecyclerview;
    Recyclerview_noticeAdapter madapter;
    RecyclerView.LayoutManager mlayoutmannaaggere;
    List<Model_notice> marraylist = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsingtoolbarlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        mrecyclerview = (RecyclerView) findViewById(R.id.mrecyclerview);
        mlayoutmannaaggere = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mrecyclerview.setLayoutManager(mlayoutmannaaggere);
        setdataa();

    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Csit Notice And Event Updates");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void setdataa(){
        String [] title = {"The updated answer is very creative ! Way to use the new ItemDecoration, it's got support for older versions of android as well","I've used this code to generate a menu that looks like an iOS menu","It is not a big project and I'm new in the android development so I want to figure out as many"};
        String[] des ={
                "It is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new " +
                        "It is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as many" +
                        "It is not a big project and I'm new in the android development so I want to figure out as manyin the android development so I want to figure out as many",
                "It is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new " +
                        "It is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as many" +
                        "It is not a big project and I'm new in the android development so I want to figure out as manyin the android development so I want to figure out as many",
                "It is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new " +
                        "It is not a big project and I'm new in the android development so I want to figure out as manyIt is not a big project and I'm new in the android development so I want to figure out as many" +
                        "It is not a big project and I'm new in the android development so I want to figure out as manyin the android development so I want to figure out as many",

        };
        String[] date = {"5 hours ago","2 hours ago","12th August"};
        for(int i = 0 ;i<date.length;i++){
            Model_notice not = new Model_notice(title[i],date[i],des[i]);
            marraylist.add(not);

        }
        madapter = new Recyclerview_noticeAdapter(this,marraylist);
        mrecyclerview.setAdapter(madapter);

    }
}
