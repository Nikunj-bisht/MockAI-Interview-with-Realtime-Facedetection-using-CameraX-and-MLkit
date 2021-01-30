package com.animesafar.dinterviewkit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

public class Drawlayeraroundface extends View {

    private Paint paint;
    Context context;
    Rect rect;
    FirebaseVisionFace firebaseVisionFace;
    float a,b;

    private   void init(){


        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawCircle(firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE).getPosition().getX(),firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE).getPosition().getY(),4,paint);
//        canvas.drawCircle(firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE).getPosition().getX(),firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE).getPosition().getY(),4,paint);
//        canvas.drawCircle(firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK).getPosition().getX(),firebaseVisionFace.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK).getPosition().getY(),4,paint);
//
//


            canvas.drawRect((float) rect.left ,(float) rect.top ,(float)rect.right,(float)rect.bottom,paint);

    }

    public  Drawlayeraroundface(Context context , Rect rect){
        super(context);


        this.context = context;
        this.rect = rect;
        init();

    }



}
