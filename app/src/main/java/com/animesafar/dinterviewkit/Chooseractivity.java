package com.animesafar.dinterviewkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.StrikethroughSpan;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class Chooseractivity extends AppCompatActivity implements Customrecyclerview.senditem {


    ArrayList<Jobs> arrayList;
    RecyclerView recyclerView;
    //AdView adView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseractivity);
      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
      getSupportActionBar().setTitle("Fields");

      arrayList = new ArrayList<Jobs>();

      recyclerView = findViewById(R.id.recycler);
        askforaudiopermission();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

       AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                super.onAdLoaded();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
                mAdView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


// TODO: Add adView to your view hierarchy.

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.guide,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.guide){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Guide");
            builder.setMessage("Select the field after that all the questions will be shown to you then click on the start button for starting your interview as this is application uses " +
                    "face detection and read your facial expressions always keep your head straight and look into the camera after that click on the click next-button for next question and " +
                    "there is a voice assistant who will read questions for you all you have to do is click on the record button ad start your interview");
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();


        }else if(item.getItemId() == R.id.feedback){

Intent intent = new Intent(Intent.ACTION_SENDTO);
intent.setData(Uri.parse("mailto:callmedudefb@gmail.com"));
startActivity(intent);

        }else if(item.getItemId() == R.id.cq){

            Intent intent = new Intent(this,CustomQuestions.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.posee){

            Intent intent = new Intent(Chooseractivity.this , Poseactivity.class);
            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }
}