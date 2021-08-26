package com.animesafar.dinterviewkit.langiden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.animesafar.dinterviewkit.R;
import com.google.common.util.concurrent.ListenableFuture;

public class Langidentifier extends AppCompatActivity implements Finderclass.helper {

    EditText textView;
    Button button;
    TextView textView2;
    Preview preview;
    PreviewView previewView;
    Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_langidentifier);

previewView = findViewById(R.id.pvr);
textView2 = findViewById(R.id.textView8);
        scanfortext();
    }

    private void scanfortext(){

        ListenableFuture<ProcessCameraProvider> listenableFuture =
                ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {

                try{

                    ProcessCameraProvider processCameraProvider = listenableFuture.get();
                    preview = new Preview.Builder()
                            .setTargetResolution(new Size(900,1900))
                            .build();

                    CameraSelector cameraSelector = new CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

                     preview.setSurfaceProvider(previewView.getSurfaceProvider());

                    ImageCapture imageCapture = new ImageCapture.Builder()
                            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                            .build();

                    // Analyzer

                    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .setTargetResolution(new Size(900,1900)).build();

                    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(Langidentifier.this),new Textanalyzer(textView2));
                    processCameraProvider.unbindAll();

                     camera = processCameraProvider.bindToLifecycle(Langidentifier.this,cameraSelector,preview,imageCapture,imageAnalysis);


                }catch (Exception e){

                }

            }
        }, ContextCompat.getMainExecutor(Langidentifier.this));



    }

}