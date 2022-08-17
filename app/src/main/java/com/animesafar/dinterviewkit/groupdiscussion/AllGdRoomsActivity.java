package com.animesafar.dinterviewkit.groupdiscussion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.animesafar.dinterviewkit.R;
import com.animesafar.dinterviewkit.groupdiscussion.repository.RetroClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllGdRoomsActivity extends AppCompatActivity implements joinRoom {

    private RecyclerView recyclerView;
    ProgressBar progressBar;
    BottomSheetDialog bottomSheetDialog;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gd_rooms);
        recyclerView = findViewById(R.id.roomsppdt);
        progressBar = findViewById(R.id.progressBar5);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.joinroombottomsheet,null));
        fetchPpdtRooms();


    }

    private void fetchPpdtRooms() {

      Call<List<PpdtRooms>> listCallback = RetroClient.getInstance().getMyApi().getPpdtRooms();
      listCallback.enqueue(new Callback<List<PpdtRooms>>() {
          @Override
          public void onResponse(Call<List<PpdtRooms>> call, Response<List<PpdtRooms>> response) {
              List<PpdtRooms> list = response.body();
              progressBar.setVisibility(View.INVISIBLE);
              LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllGdRoomsActivity.this);
              recyclerView.setAdapter(new PpdtRoomsRecyclerView(AllGdRoomsActivity.this,list,AllGdRoomsActivity.this));
              recyclerView.setLayoutManager(linearLayoutManager);
          }

          @Override
          public void onFailure(Call<List<PpdtRooms>> call, Throwable t) {
               fetchPpdtRooms();
          }
      });
    }

    @Override
    public void joinRoom(String roomName, String imageUrl) {
        bottomSheetDialog.show();
        downloadImage(imageUrl,roomName);


    }


    private void enterRoom(Bitmap response,String roomName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        response.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        startActivity(new Intent(AllGdRoomsActivity.this,GroupDiscussionActivity.class)
                .putExtra("bitImage",byteArray).putExtra("role","user"));

    }

    private void downloadImage(String imageUrl,String roomName) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest("https://ppdtserver.herokuapp.com/upload/static/images/"+imageUrl+".jpg", new com.android.volley.Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                enterRoom(response,roomName);


            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
bottomSheetDialog.cancel();
            }
        });

        requestQueue.add(imageRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bottomSheetDialog.cancel();
    }
}