package com.example.user.androidcsit.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;


import com.example.user.androidcsit.Fragment.Fragment_Notes;
import com.example.user.androidcsit.Fragment.Fragment_Solutions;
import com.example.user.androidcsit.Fragment.Fragment_oldquestions;
import com.example.user.androidcsit.Fragment.Fragment_syllabus;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/29/2018.
 */

public class SemesterDetails extends AppCompatActivity {
    private int[] tabIcons = {
            R.drawable.ic_notes,
            R.drawable.ic_syllabus,
            R.drawable.ic_questions,
            R.drawable.ic_solutions
    };
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesterdetails);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("First Semester");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.customs_tabs, null);
        tabOne.setText("Notes");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_notes, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.customs_tabs, null);
        tabTwo.setText("Syllabus");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_syllabus, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.customs_tabs, null);
        tabThree.setText("Old-Ques");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0,  R.drawable.ic_questions, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.customs_tabs, null);
        tabFour.setText("Solutions");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_solutions, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Notes(), "Notes");
        adapter.addFragment(new Fragment_syllabus(), "Syllabus");
        adapter.addFragment(new Fragment_oldquestions(), "Oldques");

        adapter.addFragment(new Fragment_Solutions(), "Solutions");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
