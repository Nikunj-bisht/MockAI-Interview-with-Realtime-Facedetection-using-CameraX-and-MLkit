package com.animesafar.dinterviewkit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Drawlayeraroundface extends View {

    private Paint paint;
    Context context;
    Rect rect;

    private   void init(){


        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10f);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

         canvas.drawRect((float) rect.left ,rect.top ,rect.right,rect.bottom,paint);

    }

    public  Drawlayeraroundface(Context context , Rect rect){
        super(context);


        this.context = context;
        this.rect = rect;
        init();

    }



}
