package com.animesafar.dinterviewkit.posepack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Points extends View {

    Context context;
    Paint paint;
    float nosex , nosey;

   Points(Context context , float nosex , float nosey){

        super(context);
        this.context = context;
           this.nosex = nosex;
           this.nosey = nosey;
        pointer();
    }

    private void pointer(){

      paint = new Paint();
      paint.setColor(Color.RED);
      paint.setAntiAlias(false);
      paint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {

         super.onDraw(canvas);
         canvas.drawCircle(nosex,nosey,25,paint);

      }

}
