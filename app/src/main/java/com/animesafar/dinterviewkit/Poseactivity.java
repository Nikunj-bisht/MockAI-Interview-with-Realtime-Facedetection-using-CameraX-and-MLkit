package com.animesafar.dinterviewkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.widget.RelativeLayout;

import com.animesafar.dinterviewkit.posepack.PoseAnalyzer;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

public class Poseactivity extends AppCompatActivity {


    Preview preview;
    PreviewView previewView;
Camera camera;
RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poseactivity);


         previewView = findViewById(R.id.poose);
relativeLayout = findViewById(R.id.relpose);
    getpermission();

    }


    private void getpermission(){

        if(ActivityCompat.checkSelfPermission(Poseactivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.CAMERA},3000);
        }else{

            readycamera();

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 3000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


            readycamera();

        }

    }

    private void readycamera(){

        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(() -> {

   try{

       ProcessCameraProvider processCameraProvider = listenableFuture.get();
       preview = new Preview.Builder().setTargetResolution(new Size(900,1700)).build();
       CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
       preview.setSurfaceProvider(previewView.getSurfaceProvider());

       ImageCapture imageCapture = new ImageCapture
               .Builder()
               .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
               .build();


       ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
               .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
               .setTargetResolution(new Size(900,1700))
               .build();

       imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(Poseactivity.this),new PoseAnalyzer(Poseactivity.this,relativeLayout));
       processCameraProvider.unbindAll();

       camera = processCameraProvider.bindToLifecycle(Poseactivity.this,cameraSelector,preview,imageCapture,imageAnalysis);


   }catch (Exception e){



   }

        }, ContextCompat.getMainExecutor(Poseactivity.this));



    }


}