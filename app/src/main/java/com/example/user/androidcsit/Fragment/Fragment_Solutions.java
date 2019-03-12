package com.example.user.androidcsit.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.androidcsit.Activity.DetailsFilesActivity;
import com.example.user.androidcsit.Adapter.ExpandableListViewAdapter;
import com.example.user.androidcsit.Model.Model_questions;
import com.example.user.androidcsit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Razu on 12/1/2017.
 */

public class Fragment_Solutions extends Fragment {

    ListView mlisview;
    private FirebaseFirestore mfirestore;
    private FirebaseAuth mauth;
    List<Model_questions> marray = new ArrayList<>();
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

    public Fragment_Solutions() {
    }
    private EditText meditetext;
    ArrayAdapter<String> madapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solutioons, container, false);
        mauth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();
        mlisview =(ListView) view.findViewById(R.id.mlistview_solution);
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
                minten.putExtra("type","Solution");
                startActivity(minten);


            }
        });

        return view;
    }
}
//    private void initview(View v){
//        expandableListView = v.findViewById(R.id.expandableListView);
//
//    }
//    private void initlisteners(){
//        //Expandablelistview on child clikc listeners
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//               String marsy = marray.get(childPosition).getLink();
//                Toast.makeText(
//                        getApplicationContext(),marsy+
//                        listDataGroup.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataGroup.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
//                return false;
//            }
//        });
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void initObjects(){
//        // initializing the list of groups
//        listDataGroup = new ArrayList<>();
//
//        // initializing the list of child
//        listDataChild = new HashMap<>();
//
//        // initializing the adapter object
//        expandableListAdapter = new ExpandableListViewAdapter(getApplicationContext(), listDataGroup, listDataChild);
//
//        // setting list adapter
//        expandableListView.setAdapter(expandableListAdapter);
//    }
//    private void initListData(){
//        final String[] sub = {"CG","TOC","DB","CS","SAD","TW"};
//
//       for(int i = 0;i<sub.length;i++){
//           listDataGroup.add(sub[i]);
//        mfirestore.collection("Question").document("Fourth Semester").collection(sub[i]).addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//
//                if (!documentSnapshots.isEmpty()) {
//                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
//                        if (doc.getType() == DocumentChange.Type.ADDED) {
//                            String docid = doc.getDocument().get("link").toString();
//                            String docids = doc.getDocument().getId();
//
//                            cg.add(docids);
//                                Model_questions mquestions = new Model_questions(docid, docids);
//                                marray.add(mquestions);
////                                madapter.notifyDataSetChanged();
//                            String s = "sksksk \"kskskk\";ssdlk";
//                            Log.d("fragmentss", "onEvent: kk" + docids+docid);
//                        }
//                    }
//                }
//            }
//        });
        //}
//        }
        // Adding group data
//        listDataGroup.add("CG");
//        listDataGroup.add("TOC");
//        listDataGroup.add("DBMS");
//        listDataGroup.add("TW");
//        listDataGroup.add("SAD");
//        listDataGroup.add("CS");
        // array of strings
//        String[] array;
//
//        // list of alcohol
//        List<String> alcoholList = new ArrayList<>();
//        array = getResources().getStringArray(R.array.string_array_alcohol);
//        for (String item : array) {
//            alcoholList.add(item);
//        }
//        // list of coffee
//        List<String> coffeeList = new ArrayList<>();
//        array = getResources().getStringArray(R.array.string_array_coffee);
//        for (String item : array) {
//            coffeeList.add(item);
//        }
//        // list of pasta
//        List<String> pastaList = new ArrayList<>();
//        array = getResources().getStringArray(R.array.string_array_pasta);
//        for (String item : array) {
//            pastaList.add(item);
//        }
//        // list of cold drinks
//        List<String> coldDrinkList = new ArrayList<>();
//        array = getResources().getStringArray(R.array.string_array_cold_drinks);
//        for (String item : array) {
//            coldDrinkList.add(item);
//        }
//        // Adding child data
////        listDataChild.put(listDataGroup.get(0), cg);
//        listDataChild.put(listDataGroup.get(1), coffeeList);
//        listDataChild.put(listDataGroup.get(2), pastaList);
//        listDataChild.put(listDataGroup.get(3), coldDrinkList);

//        // notify the adapter
//        expandableListAdapter.notify();


//    }
//
//}
