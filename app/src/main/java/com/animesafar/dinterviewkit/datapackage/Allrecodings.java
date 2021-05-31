package com.animesafar.dinterviewkit.datapackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import com.animesafar.dinterviewkit.R;

import java.io.File;
import java.util.ArrayList;

public class Allrecodings extends AppCompatActivity implements Recodingsadaptor.selected {


    ArrayList<File> arrayList;
    MediaPlayer mediaPlayer;
ListView listView;
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
         File[] files = file.listFiles();

         for(int i=0;i<files.length;i++){

             arrayList.add(files[i]);

         }

           Recodingsadaptor recodingsadaptor = new Recodingsadaptor(this,arrayList,this);
listView.setAdapter(recodingsadaptor);
    }

    @Override
    public void onselected(int pos) {


if(mediaPlayer.isPlaying()){
    mediaPlayer.stop();
           mediaPlayer = new MediaPlayer();



        }else{
    mediaPlayer = new MediaPlayer();

}


        try {
    mediaPlayer.setDataSource(arrayList.get(pos).getAbsolutePath());
    mediaPlayer.prepare();
    mediaPlayer.start();

}catch (Exception e){
    e.printStackTrace();
}

    }
}