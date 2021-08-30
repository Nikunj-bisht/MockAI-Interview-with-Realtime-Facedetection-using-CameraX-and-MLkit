package com.animesafar.dinterviewkit.Recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.animesafar.dinterviewkit.R;
import com.animesafar.dinterviewkit.datapackage.Griddata;

import java.util.ArrayList;

public class GridRecycler extends RecyclerView.Adapter<Gridviewholder> {

    Context context;
    ArrayList<Griddata> arrayList;
    LayoutInflater layoutInflater;
    callback cll;

    public GridRecycler(Context context , ArrayList<Griddata> arrayList , callback call){

        this.context = context;
        this.arrayList = arrayList;
        this.cll = call;
        layoutInflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public Gridviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.gridview , parent , false);
        Gridviewholder gridviewholder = new Gridviewholder(view);

        return  gridviewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull Gridviewholder holder, int position) {

        holder.getTextView().setText(arrayList.get(position).getTitle());
        final int i = position;
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cll.touch(i);

            }
        });
        new Pictures(position , holder.getImageView()).start();

    }

    class  Pictures extends Thread{

        int i;
        ImageView imageView;
        Pictures(int pos , ImageView imageView){
            this.i = pos;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            ImageRequest imageRequest = new ImageRequest("https://safetyapiforw.herokuapp.com"+arrayList.get(i).getUrl(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {

                    imageView.setImageBitmap(response);

                }
            }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {



                }
            });

            requestQueue.add(imageRequest);

        }
    }


    @Override
    public int getItemCount() {

        return arrayList.size();

    }
}
