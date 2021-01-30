package com.animesafar.dinterviewkit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.CameraView;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.animesafar.dinterviewkit.Recycler.Jobs;
import com.animesafar.dinterviewkit.datapackage.Allrecodings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Camera camera;
    Preview preview;
    ImageCapture imageCapture;
    PreviewView previewView;
    TextToSpeech textToSpeech;
    RelativeLayout view;
    int value=0;
    FloatingActionButton floatingActionButton;
    MediaRecorder mediaRecorder;
    boolean isrecording = false;
    File file ;
    Activity activity;
    String ques;
    Button button;
    Button again;
    Button butt;
    ArrayList<Jobs> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
previewView = findViewById(R.id.pv);
view = findViewById(R.id.conlay);
button = findViewById(R.id.sr);
floatingActionButton = findViewById(R.id.floatingActionButton2);
again = findViewById(R.id.aa);
again.setOnClickListener(this);
floatingActionButton.setOnClickListener(this);
button.setOnClickListener(this);

butt = findViewById(R.id.next);
butt.setOnClickListener(this);

activity = this;
file = activity.getExternalFilesDir("Recordings");
arrayList = (ArrayList<Jobs>) getIntent().getSerializableExtra("questions");
//ques = getIntent().getStringExtra("ques");

//value = getIntent().getIntExtra("Values",0);

         textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
//
//                textToSpeech.setVoice(new Voice("en"));
                textToSpeech.setLanguage(Locale.ENGLISH);
                new Speakerthread().start();

            }
        });

        checkpermissions();

    }


    private void checkpermissions(){



        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},2000);


        }else{

          //  renderimage();

        }


    }

    private void renderimage()  {

       ListenableFuture<ProcessCameraProvider> val =  ProcessCameraProvider.getInstance(this);

           val.addListener(new Runnable() {
               @RequiresApi(api = Build.VERSION_CODES.P)
               @Override
               public void run() {

                   try {

                     ProcessCameraProvider processCameraProvider= val.get();
                     preview = new Preview.Builder().setTargetResolution(new Size(900,1200)).build();

//                  ImageAnalysis imageAnalysis =   new ImageAnalysis.Builder().build();
//                    imageAnalysis.setAnalyzer(getMainExecutor(),new BaseImage());

                       CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();

                       preview.setSurfaceProvider(previewView.getSurfaceProvider());

          ImageCapture imageCapture =   new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();
                   ImageAnalysis imageAnalysis1 =   new  ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).setTargetResolution(new Size(900,1200)).build();
                   imageAnalysis1.setAnalyzer(ContextCompat.getMainExecutor(MainActivity.this),new BaseImage(MainActivity.this,textToSpeech,view,findViewById(R.id.textView3)));
                       processCameraProvider.unbindAll();

                       camera = processCameraProvider.bindToLifecycle(MainActivity.this,cameraSelector,preview,imageCapture,imageAnalysis1);
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }
           }, ContextCompat.getMainExecutor(MainActivity.this));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 2000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Granted permission",Toast.LENGTH_LONG).show();
            renderimage();

        }


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.floatingActionButton2){

            if(isrecording){

                stoprecording();
            isrecording = false;
            }else{
                isrecording = true;
                startrecording();

            }

        }
        else if(view.getId() == R.id.sr){

Intent intent = new Intent(this, Allrecodings.class);
startActivity(intent);

        } else if(view.getId() == R.id.next){

            textToSpeech.speak(arrayList.get(value).getDescription(),TextToSpeech.QUEUE_ADD,null,null);
value++;

        }
        else if(view.getId() == R.id.aa){
int repeat = value-1;
            textToSpeech.speak(arrayList.get(repeat).getDescription(),TextToSpeech.QUEUE_ADD,null,null);


        }

    }

    private void stoprecording() {
        floatingActionButton.setImageResource(R.drawable.ic_baseline_mic_24);
        if(button.getVisibility() == View.INVISIBLE){

            button.setVisibility(View.VISIBLE);

        }
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
      //  butt.setVisibility(View.VISIBLE);

    }

    private void startrecording(){

        if(button.getVisibility() == View.VISIBLE){

            button.setVisibility(View.INVISIBLE);

        }

        Date date = new Date();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("HH:mm:ss"));
 String time = simpleDateFormat.format(date);
        File file1 = new File(file+"/"+"Recording_"+date.getTime()+".3gp");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(file1);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        floatingActionButton.setImageResource(R.drawable.ic_baseline_record_voice_over_24);
       try{
           mediaRecorder.prepare();

       }catch (Exception e){
e.printStackTrace();
       }
        mediaRecorder.start();

    }

    class  Speakerthread extends Thread{

        @Override
        public void run() {

            textToSpeech.speak(arrayList.get(value).getDescription(),TextToSpeech.QUEUE_ADD,null,null);
            value++;
            try {
                Thread.sleep(3000);
                renderimage();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }
}