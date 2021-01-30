package com.animesafar.dinterviewkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.animesafar.dinterviewkit.Recycler.Customrecyclerview;
import com.animesafar.dinterviewkit.Recycler.Jobs;
import com.animesafar.dinterviewkit.datapackage.Customadaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Questionslist extends AppCompatActivity implements Customadaptor.quesclicked {


    public ArrayList<Jobs> arrayList = new ArrayList<>();
    ListView listView;
    ArrayAdapter arrayAdapter;
    String itemvalue;
    Button start;
public   static int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionslist);
        getSupportActionBar().hide();

        itemvalue = getIntent().getStringExtra("??");
start = findViewById(R.id.st);
start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Questionslist.this,MainActivity.class);
        intent.putExtra("questions",arrayList);
        //  intent.putExtra("Values",i);
        startActivity(intent);
    }
});
        listView = findViewById(R.id.list);

        fetchquestionsfromdb();



    }

    private void fetchquestionsfromdb() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://safetyapiforw.herokuapp.com/api/questions";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("jobprofile",itemvalue);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("--------->",response.toString());
                    JSONArray jsonArrayRequest = response.getJSONArray("qs");
                    for(int i=0;i<jsonArrayRequest.length();i++){

                        String ti=jsonArrayRequest.getJSONObject(i).getString("title");
try {
    String de=jsonArrayRequest.getJSONObject(i).getString("questions");
    Jobs jobs = new Jobs(ti,de);
    arrayList.add(jobs);

}catch (Exception e){
    e.printStackTrace();
}

                    }
                    findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                    listView.setAdapter(new Customadaptor(Questionslist.this,Questionslist.this,arrayList));
start.setVisibility(View.VISIBLE);
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
    public void clicked(int i) {
counter++;
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("questions",arrayList);
      //  intent.putExtra("Values",i);
        startActivity(intent);

    }
}