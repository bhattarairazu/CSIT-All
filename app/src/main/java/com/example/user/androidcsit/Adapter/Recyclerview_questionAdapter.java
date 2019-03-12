package com.example.user.androidcsit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.user.androidcsit.Model.Model_questions;
import com.example.user.androidcsit.R;
import com.example.user.androidcsit.Utils;


import java.util.List;

//import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by User on 9/18/2018.
 */

public class Recyclerview_questionAdapter extends RecyclerView.Adapter<Recyclerview_questionAdapter.MyViewHolder> {
    private List<Model_questions> mquestions;
    private Context mcontext;

    public Recyclerview_questionAdapter(List<Model_questions> mquestions, Context mcontext) {
        this.mquestions = mquestions;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_filedownload,parent,false);

        return new MyViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model_questions qns = mquestions.get(position);
        holder.mtextview.setText("Computer Graphics Questions " + qns.getName());

    }

    @Override
    public int getItemCount() {
        return mquestions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mtextview;
        public MyViewHolder(View itemView) {
            super(itemView);
            mtextview =(TextView) itemView.findViewById(R.id.mtextview_qncg);
            mtextview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
              String dirPath = Utils.getRootDirPath(mcontext);
            int id = getAdapterPosition();
            final int[] downloadIdOne = new int[1];
            String link = mquestions.get(id).getLink();
            Toast.makeText(mcontext, "Downloading link :"+link, Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            View viees = LayoutInflater.from(mcontext).inflate(R.layout.alertdialog_downloa,null);
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
            downloadIdOne[0] = PRDownloader.download(link, dirPath, "CG_Question_"+mquestions.get(id).getName())
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
                            buttonOne.setText("Start");
                            Toast.makeText(mcontext, "Download Eerror Occoured", Toast.LENGTH_SHORT).show();
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

}
