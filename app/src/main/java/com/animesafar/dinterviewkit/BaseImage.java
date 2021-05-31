package com.animesafar.dinterviewkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

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
    boolean right = true;
    boolean down =  true;
    boolean left = true;
    boolean smile = true;
    RelativeLayout rootview;
    TextView textView;

public BaseImage(Context context,TextToSpeech textToSpeech,RelativeLayout view,TextView textView){

    this.context = context;
this.textToSpeech = textToSpeech;
this.rootview = view;
this.textView = textView;
}


    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void analyze(@NonNull ImageProxy image) {

       Image n =  image.getImage();
      //  Log.d("RAn analyzer ------------------->>>>>>>>>>" ,"Ran");


      FirebaseVisionFaceDetectorOptions a =    new FirebaseVisionFaceDetectorOptions.Builder().setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
.setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
              .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
              .setMinFaceSize(0.2f)
              .enableTracking()

              .build();



      int rotation = image.getImageInfo().getRotationDegrees();



     FirebaseVisionImage p =  FirebaseVisionImage.fromMediaImage(n, finddegree(rotation));

        FirebaseVisionFaceDetector detector =   FirebaseVision.getInstance().getVisionFaceDetector(a);




                detector.detectInImage(p).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
          @Override
          public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {

            //  Log.d("Got values ------------------->>>>>>>>>>" , firebaseVisionFaces.toString());

              if(!firebaseVisionFaces.isEmpty()){

                  textView.setText("Face detected");

                  for(int i=0;i<firebaseVisionFaces.size();i++){
if(rootview.getChildCount()>1){
    rootview.removeViewAt(1);
}

//
//    if(firebaseVisionFaces.get(i).getHeadEulerAngleY()>20 && right){
//
//        Log.d("Right degree--------------------------->>>>>>",String.valueOf(firebaseVisionFaces.get(i).getHeadEulerAngleY()));
//      textToSpeech.speak("Right side",TextToSpeech.QUEUE_FLUSH,null,null);
//
//    right = false;
//    }

    FirebaseVisionFace firebaseVisionFace = firebaseVisionFaces.get(i);

    FirebaseVisionPoint firebaseVisionPoint = firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE).getPosition();
    FirebaseVisionPoint firebaseVisionPoint1 = firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM).getPosition();

    double x = Math.pow(firebaseVisionPoint1.getX() - firebaseVisionPoint.getX() , 2);
    double y = Math.pow(firebaseVisionPoint1.getY() - firebaseVisionPoint.getY() , 2);



   // Log.d("Head down figures------->>>>" ,Math.sqrt(x + y) +" "+ ((0.125) * (firebaseVisionFace.getBoundingBox().width()*1.5)));

      if(Math.sqrt(x + y) < ((0.111) * (firebaseVisionFace.getBoundingBox().width()*1.5))){

             textToSpeech.speak("Dont look down see straight",TextToSpeech.QUEUE_FLUSH,null,null);
          textView.setText("Looking down");


      }


    if(firebaseVisionFace.getHeadEulerAngleY() < -20 || firebaseVisionFace.getHeadEulerAngleY() >20){
        textView.setText("look straight");

        // left = false;

    }

//    if(firebaseVisionFace.getHeadEulerAngleZ() > 4){
//
//
//        textToSpeech.speak("Dont look down see straight",TextToSpeech.QUEUE_FLUSH,null,null);
//down = false;
//
//
//
//    }
    if(firebaseVisionFace.getSmilingProbability()>0.6){

        textView.setText("Happy face");
       // textToSpeech.speak("dont laugh too much",TextToSpeech.QUEUE_FLUSH,null,null);

//        smile = false;
    }


    View view1 =  new Drawlayeraroundface(context,firebaseVisionFace.getBoundingBox());
rootview.addView(view1);
   /*  This is part of project added functionality of textToSpeech*/

//    Log.d("Got values ------------------->>>>>>>>>>" , firebaseVisionFaces.get(i).toString());
//boolean stm = true;
//   float smiling_value =  firebaseVisionFaces.get(i).getSmilingProbability();
//float eyes = firebaseVisionFaces.get(i).getLeftEyeOpenProbability();
//    if(smiling_value  < 0.4 && speakornot){
//speakornot  = false;
//      // textToSpeech.speak("Please Smile nikunj",TextToSpeech.QUEUE_FLUSH,null,null);
//
//    }
//
//    if(eyes < 0.3){
//
//        eyesopened = false;
//
//    }
//
//
//     if(firebaseVisionFaces.get(i).getLeftEyeOpenProbability() < 0.3 && !eyesopened) {
//       textToSpeech.speak("Please open your eyes", TextToSpeech.QUEUE_FLUSH, null, null);
//
//
//
//    }
//
//Toast.makeText(context,"Detected",Toast.LENGTH_LONG).show();

}


              }else{
                  if(rootview.getChildCount()>1){
                      rootview.removeViewAt(1);
                  }


                  textView.setText("Face Not detected");

                 // Toast.makeText(context,"Not Detected",Toast.LENGTH_LONG).show();

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

    private int finddegree(int rotation) {
        int result;

        switch (rotation){
        case 0 :

            result  = FirebaseVisionImageMetadata.ROTATION_0;
        break;
        case 90 :

            result  = FirebaseVisionImageMetadata.ROTATION_90;
            break;
        case 180 :

            result  = FirebaseVisionImageMetadata.ROTATION_180;
            break;
        case 270 :

            result  = FirebaseVisionImageMetadata.ROTATION_270;
            break;

            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
    }
        return  result;

    }
//
//    private int firebasedegreerotation() {
//
//
//
//
//    }



}
