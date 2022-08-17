package com.animesafar.dinterviewkit.datapackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.animesafar.dinterviewkit.R;
import com.chibde.visualizer.CircleBarVisualizer;
import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.gauravk.audiovisualizer.visualizer.CircleLineVisualizer;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;

public class Allrecodings extends AppCompatActivity implements Recodingsadaptor.selected {


    ArrayList<File> arrayList;
    MediaPlayer mediaPlayer;
    ListView listView;
    BottomSheetDialog bottomSheetDialog;
    CircleBarVisualizer circleLineVisualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrecodings);
        getSupportActionBar().hide();
        listView = findViewById(R.id.li);
        arrayList = new ArrayList<>();
        Activity activity = this;
        File file = activity.getExternalFilesDir("Recordings");
        mediaPlayer = new MediaPlayer();
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.playaudio, null));
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer = new MediaPlayer();
                }
            }
        });

        File[] files = file.listFiles();

        for (int i = 0; i < files.length; i++) {

            arrayList.add(files[i]);

        }

        Recodingsadaptor recodingsadaptor = new Recodingsadaptor(this, arrayList, this);
        listView.setAdapter(recodingsadaptor);
    }

    @Override
    public void onselected(int pos) {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    bottomSheetDialog.dismiss();
                }
            });
            mediaPlayer.setDataSource(arrayList.get(pos).getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            bottomSheetDialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDelete(int pos) {
        File fileToBeDelete = arrayList.get(pos);
        fileToBeDelete.delete();
        arrayList.remove(pos);
        Recodingsadaptor recodingsadaptor = new Recodingsadaptor(this, arrayList, this);
        listView.setAdapter(recodingsadaptor);
    }
}