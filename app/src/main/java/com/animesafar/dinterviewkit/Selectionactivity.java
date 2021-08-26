package com.animesafar.dinterviewkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animesafar.dinterviewkit.Recycler.GridRecycler;
import com.animesafar.dinterviewkit.datapackage.Griddata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Selectionactivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Griddata> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectionactivity);
        getSupportActionBar().hide();

        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.rec);


        fetchtasks();


    }

    private void fetchtasks(){
String url = "https://safetyapiforw.herokuapp.com/tasks";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url , null , new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try{

                    JSONArray jsonArray = response.getJSONArray("data");
                     for(int i=0;i<jsonArray.length();i++){
                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                           arrayList.add(new Griddata(jsonObject.getString("type") , jsonObject.getString("url")));
                     }
                     System.out.println(arrayList);
                    showdata();

                }catch (Exception e){

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

requestQueue.add(jsonObjectRequest);


    }

    private void showdata() {

        GridRecycler gridRecycler = new GridRecycler(this,arrayList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gridRecycler);



    }

}