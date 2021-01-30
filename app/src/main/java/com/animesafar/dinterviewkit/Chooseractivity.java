package com.animesafar.dinterviewkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animesafar.dinterviewkit.Recycler.Customrecyclerview;
import com.animesafar.dinterviewkit.Recycler.Jobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Chooseractivity extends AppCompatActivity implements Customrecyclerview.senditem {


    ArrayList<Jobs> arrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseractivity);
        getSupportActionBar().hide();
arrayList = new ArrayList<Jobs>();
recyclerView = findViewById(R.id.recycler);
        askforaudiopermission();


    }

    private void askforaudiopermission() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO} , 3000);



        }else{

            showallfields();

        }


    }

    private void showquestionszctivity() {

        Intent intent = new Intent(this,Questionslist.class);

        startActivity(intent);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

          if(requestCode == 3000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
             showallfields();

          }else{
              askforaudiopermission();
          }


    }

    private void showallfields() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://safetyapiforw.herokuapp.com/api/jobssector";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("location","selaqui");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArrayRequest = response.getJSONArray("joby");
                    for(int i=0;i<jsonArrayRequest.length();i++){

                        String ti=jsonArrayRequest.getJSONObject(i).getString("title");

                        String de=jsonArrayRequest.getJSONObject(i).getString("description");
                        Jobs jobs = new Jobs(ti,de);
                        arrayList.add(jobs);

                    }
                       findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Chooseractivity.this);

                    recyclerView.setAdapter(new Customrecyclerview(Chooseractivity.this,arrayList,Chooseractivity.this));
                   recyclerView.setLayoutManager(linearLayoutManager);
                    // displayinrecyclerview(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    @Override
    public void openquestions(String item) {

        Intent intent = new Intent(this,Questionslist.class);
        intent.putExtra("??",item);

         startActivity(intent);


    }
}