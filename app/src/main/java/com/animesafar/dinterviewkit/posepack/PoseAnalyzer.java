package com.animesafar.dinterviewkit.posepack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PoseAnalyzer implements ImageAnalysis.Analyzer {
Context context;

public PoseAnalyzer(Context context){

    this.context = context;

}

    @SuppressLint("UnsafeExperimentalUsageError")

    @Override
    public void analyze(@NonNull  ImageProxy image) {

        Image image1 = image.getImage();

        PoseDetectorOptions poseDetectorOptions = new PoseDetectorOptions
                .Builder()
                .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
                .build();

        PoseDetector poseDetector = PoseDetection.getClient(poseDetectorOptions);
        InputImage inputImage = InputImage.fromMediaImage(image1,image.getImageInfo().getRotationDegrees());

         poseDetector.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<Pose>() {
                    @Override

                    public void onSuccess(Pose pose) {

                        List<PoseLandmark> landmarks = pose.getAllPoseLandmarks();

                         PoseLandmark poseLandmark = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
                        PoseLandmark poseLandmark2 = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
                        PoseLandmark poseLandmark3 = pose.getPoseLandmark(PoseLandmark.NOSE);
new Points(context ,poseLandmark3.getPosition().x , poseLandmark3.getPosition().y);
                        Log.d(" Here--->>>> " , poseLandmark.getPosition()+" "+poseLandmark2.getPosition()+" "+poseLandmark3.getPosition());
image.close();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

e.printStackTrace();



                        Log.d("Error ---> " , "error occured");
image.close();
                    }
                });

    }
}
