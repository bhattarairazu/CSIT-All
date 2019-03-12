package com.example.user.androidcsit.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.example.user.androidcsit.Adapter.CustomDownloadAdapter;
import com.example.user.androidcsit.Downloadclass;
import com.example.user.androidcsit.Model.Model_questions;
import com.example.user.androidcsit.R;
import com.example.user.androidcsit.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailsFilesActivity extends AppCompatActivity {
    private ListView mlistvies;
    FirebaseAuth mauth;
    FirebaseFirestore mfirestore;
    String subjects,type;
    List<Model_questions> marray = new ArrayList<>();
    List<String> mlist = new ArrayList<>();
    CustomDownloadAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_files);
        final String dirPath = Utils.getRootDirPath(this);
        mlistvies = (ListView) findViewById(R.id.mlistview_detailsfiles);
        Bundle mbunde = getIntent().getExtras();
        if(mbunde!=null){
            subjects = mbunde.getString("name");
            type = mbunde.getString("type");
        }
        mauth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();
        if(mauth.getCurrentUser()!=null){
            mfirestore.collection("Files").document(subjects).collection(type).addSnapshotListener(this, new EventListener<QuerySnapshot>() {

                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String docid = doc.getDocument().get("link").toString();
                                String docids = doc.getDocument().getId();

                                mlist.add(docids);
                                Model_questions mquestions = new Model_questions(docid, docids);
                                marray.add(mquestions);
                                madapter.notifyDataSetChanged();
                                String s = "sksksk \"kskskk\";ssdlk";
                                Log.d("fragmentss", "onEvent: kk" + docids+docid);
                            }
                        }
                    }
                }
            });
            madapter = new CustomDownloadAdapter(marray,getApplicationContext());
            mlistvies.setAdapter(madapter);
        }
        final Downloadclass mclas = new Downloadclass();
        mlistvies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String links = marray.get(position).getLink();
                Log.d("link", "onItemClick: nnnnnnnnn"+links);
               download(links,dirPath,subjects+position);
            }
        });

    }
    public void download( String link, String dirPath, String name){
        final int[] downloadIdOne = new int[1];
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viees = LayoutInflater.from(this).inflate(R.layout.alertdialog_downloa,null);
        final Button buttonOne= (Button)viees.findViewById(R.id.buttonOne);
        final Button buttonCancelOne = (Button)viees.findViewById(R.id.buttonCancelOne);
        final TextView textViewProgressOne = (TextView)viees.findViewById(R.id.textViewProgressOne);
        final ProgressBar progressBarOne = (ProgressBar)viees.findViewById(R.id.progressBarOne);


        builder.setView(viees);
        final AlertDialog[] mdialoges = {builder.create()};
        mdialoges[0].show();
        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne[0])) {
            PRDownloader.pause(downloadIdOne[0]);
            return;
        }
        buttonOne.setEnabled(false);
        progressBarOne.setIndeterminate(true);
        progressBarOne.getIndeterminateDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne[0])) {
            PRDownloader.resume(downloadIdOne[0]);
            return;
        }
        downloadIdOne[0] = PRDownloader.download(link, dirPath, name)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        progressBarOne.setIndeterminate(false);
                        buttonOne.setEnabled(true);
                        buttonOne.setText("Pause");
                        buttonCancelOne.setEnabled(true);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        buttonOne.setText("Resume");
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        buttonOne.setText("Start");
                        buttonCancelOne.setEnabled(false);
                        progressBarOne.setProgress(0);
                        textViewProgressOne.setText("");
                        downloadIdOne[0] = 0;
                        progressBarOne.setIndeterminate(false);
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        progressBarOne.setProgress((int) progressPercent);
                        textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                        progressBarOne.setIndeterminate(false);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        buttonOne.setEnabled(false);
                        buttonCancelOne.setEnabled(false);
                        buttonOne.setText("Download Completed");
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d("fragmentss", "onError: ,,,"+error.toString());
                        buttonOne.setText("Start");
                        Toast.makeText(DetailsFilesActivity.this, "Download Eerror Occoured", Toast.LENGTH_SHORT).show();
                        textViewProgressOne.setText("");
                        progressBarOne.setProgress(0);
                        downloadIdOne[0] = 0;
                        buttonCancelOne.setEnabled(false);
                        progressBarOne.setIndeterminate(false);
                        buttonOne.setEnabled(true);
                    }


                });
        buttonCancelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PRDownloader.cancel(downloadIdOne);
            }
        });
    }

}
