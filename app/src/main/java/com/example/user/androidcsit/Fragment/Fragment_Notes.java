package com.example.user.androidcsit.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.androidcsit.Activity.DetailsFilesActivity;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Razu on 12/1/2017.
 */

public class Fragment_Notes extends Fragment {
    ListView mlisview;

    public Fragment_Notes() {
    }
    private static final String[] subject = {
            "Micro Processor",
            "Digital Logic",
            "Theory Of Computing",
            "Cognitive Science",
            "Database",
            "Technical Writing",
            "System Analysis And Design",
            "Computer Graphics",
            "Object Oriented Programming",
            "Principle Of Management",
            "Numerical Method",
            "Discrete Structure",
            "C",
            "Data Structure And Algorithm",
            "Computer Architecture"
    };
    List<String> mlist = new ArrayList<>();
    private EditText meditetext;
    ArrayAdapter<String> madapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        mlisview =(ListView) view.findViewById(R.id.mlistview);
        meditetext = (EditText) view.findViewById(R.id.medittext_search);
        for(int i = 0;i<subject.length;i++){
            mlist.add(subject[i]);
        }
        madapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mlist);
        mlisview.setAdapter(madapter);
        meditetext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                madapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mlisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Intent minten = new Intent(view.getContext(), DetailsFilesActivity.class);

                minten.putExtra("name",name);
                minten.putExtra("type","Notes");
                startActivity(minten);


            }
        });

        return view;
    }

}
