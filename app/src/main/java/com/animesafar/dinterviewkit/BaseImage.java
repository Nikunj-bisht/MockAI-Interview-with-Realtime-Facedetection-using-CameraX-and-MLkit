package com.animesafar.dinterviewkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class BaseImage implements ImageAnalysis.Analyzer {

    FirebaseVisionFaceDetector firebaseVisionFaceDetector;
//
//           FirebaseVisionFaceDetectorOptions.ContourMod
//
//    FirebaseVision
    Context context;
    TextToSpeech textToSpeech;
    boolean speakornot = true;
    boolean eyesopened = true;
    RelativeLayout rootview;
public BaseImage(Context context,TextToSpeech textToSpeech,RelativeLayout view){

    this.context = context;
this.textToSpeech = textToSpeech;
this.rootview = view;
}


    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void analyze(@NonNull ImageProxy image) {

       Image n =  image.getImage();
        Log.d("RAn analyzer ------------------->>>>>>>>>>" ,"Ran");


      FirebaseVisionFaceDetectorOptions a =    new FirebaseVisionFaceDetectorOptions.Builder().setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
.setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS).setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS).enableTracking().build();


      int rotation = image.getImageInfo().getRotationDegrees();




     FirebaseVisionImage p =  FirebaseVisionImage.fromMediaImage(n, 0);

        FirebaseVisionFaceDetector detector =   FirebaseVision.getInstance().getVisionFaceDetector(a);



                detector.detectInImage(p).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
          @Override
          public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {

              Log.d("Got values ------------------->>>>>>>>>>" , firebaseVisionFaces.toString());

              if(firebaseVisionFaces!=null){

for(int i=0;i<firebaseVisionFaces.size();i++){

//
//    View view1 =  new Drawlayeraroundface(context,firebaseVisionFaces.get(i).getBoundingBox());
//rootview.addView(view1);
   /*  This is part of project added functionality of textToSpeech*/

    Log.d("Got values ------------------->>>>>>>>>>" , firebaseVisionFaces.get(i).toString());
boolean stm = true;
   float smiling_value =  firebaseVisionFaces.get(i).getSmilingProbability();

    if(smiling_value  < 0.4 && speakornot){
speakornot  = false;
        textToSpeech.speak("Please Smile nikunj",TextToSpeech.QUEUE_FLUSH,null,null);

    }

    else if(firebaseVisionFaces.get(i).getLeftEyeOpenProbability() < 0.2 && eyesopened) {
        textToSpeech.speak("Please open your eyes", TextToSpeech.QUEUE_FLUSH, null, null);

              eyesopened = false;

    }

Toast.makeText(context,"Detected",Toast.LENGTH_LONG).show();



}


              }else{
                  Toast.makeText(context,"Not Detected",Toast.LENGTH_LONG).show();

              }

              image.close();


          }

                }
      ).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(context,"Not Detected",Toast.LENGTH_LONG).show();
              image.close();

          }

                });


    }

    private int firebasedegreerotation() {
        return 0;
    }


}
