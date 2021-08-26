package com.animesafar.dinterviewkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animesafar.dinterviewkit.Recycler.Customrecyclerview;
import com.animesafar.dinterviewkit.Recycler.Jobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Landingpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        getSupportActionBar().hide();


        connectwithserver();


    }

    private void connectwithserver() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://safetyapiforw.herokuapp.com/api/jobssector";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Intent intent = new Intent(Landingpage.this,Selectionactivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


          //      Intent intent = new Intent(Landingpage.this,Chooseractivity.class);
                Intent intent = new Intent(Landingpage.this,Selectionactivity.class);

                startActivity(intent);

            }
        });

        requestQueue.add(jsonObjectRequest);



    }
}