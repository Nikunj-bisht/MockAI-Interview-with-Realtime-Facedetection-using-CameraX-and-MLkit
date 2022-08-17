package com.animesafar.dinterviewkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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

import com.animesafar.dinterviewkit.Recycler.callback;
import com.animesafar.dinterviewkit.extraactivities.Ieltsactivity;
import com.animesafar.dinterviewkit.groupdiscussion.AllGdRoomsActivity;
import com.animesafar.dinterviewkit.groupdiscussion.GroupDiscussionActivity;

public class Selectionactivity extends AppCompatActivity implements callback {

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


    private void fetchtasks() {
        String url = "https://safetyapiforw.herokuapp.com/tasks";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new Griddata(jsonObject.getString("type"), jsonObject.getString("url")));
                    }
                    System.out.println(arrayList);
                    showdata();

                } catch (Exception e) {

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

        GridRecycler gridRecycler = new GridRecycler(this, arrayList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gridRecycler);


    }

    @Override
    public void touch(int i) {
        Toast.makeText(this, "Touched", Toast.LENGTH_LONG).show();
        switch (i) {

            case 0:

                Intent intent = new Intent(this, Chooseractivity.class);
                startActivity(intent);

                break;

            case 1:

                Intent intent1 = new Intent(this, Ieltsactivity.class);
                startActivity(intent1);

                break;
            case 2:
                Intent intent2 = new Intent(this, AllGdRoomsActivity.class);
                startActivity(intent2);
        }

    }
}