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
import android.content.pm.PackageManager;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Size;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    Camera camera;
    Preview preview;
    ImageCapture imageCapture;
    PreviewView previewView;
    TextToSpeech textToSpeech;
    RelativeLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
previewView = findViewById(R.id.pv);
view = findViewById(R.id.conlay);

checkpermissions();

         textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
//
//                textToSpeech.setVoice(new Voice("en"));
                textToSpeech.setLanguage(Locale.ENGLISH);

            }
        });

    }


    private void checkpermissions(){


        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},2000);


        }else{

            renderimage();

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
                     preview = new Preview.Builder().setTargetResolution(new Size(400,400)).build();

//                  ImageAnalysis imageAnalysis =   new ImageAnalysis.Builder().build();
//                    imageAnalysis.setAnalyzer(getMainExecutor(),new BaseImage());

                       CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();

                       preview.setSurfaceProvider(previewView.getSurfaceProvider());

          ImageCapture imageCapture =   new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();
                   ImageAnalysis imageAnalysis1 =   new  ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
                   imageAnalysis1.setAnalyzer(ContextCompat.getMainExecutor(MainActivity.this),new BaseImage(MainActivity.this,textToSpeech,view));
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
}