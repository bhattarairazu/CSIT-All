package com.example.user.androidcsit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
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

/**
 * Created by User on 9/29/2018.
 */

public class Downloadclass {
    public Downloadclass() {
    }

    public void download(final Context mactiviyt, String link, String dirPath, String name){
        final int[] downloadIdOne = new int[1];
        final AlertDialog.Builder builder = new AlertDialog.Builder(mactiviyt);
        View viees = LayoutInflater.from(mactiviyt).inflate(R.layout.alertdialog_downloa,null);
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
                        buttonOne.setText("Start");
                        Toast.makeText(mactiviyt, "Download Eerror Occoured", Toast.LENGTH_SHORT).show();
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

