package com.animesafar.dinterviewkit.extraactivities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Requestclassforextraactivity {

    public static void api_request_fortopics(Context context , String url , funcall fun ){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    ArrayList<Datafortopics> arrayList = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        try{
                            String topic = jsonArray.getJSONObject(i).getString("title");

                            String code = jsonArray.getJSONObject(i).getString("Code");
                            arrayList.add(new Datafortopics(topic , code));
                        }catch (Exception e){

                        }



                    }
                    fun.vollycallback(arrayList);

                } catch (JSONException e) {
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



}
