package com.animesafar.dinterviewkit.extraactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.animesafar.dinterviewkit.R;

import java.util.ArrayList;

public class Ieltsactivity extends AppCompatActivity implements gotochatinterface {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ieltsactivity);

        recyclerView = findViewById(R.id.recact);
        fetchalltopics();

    }

    protected void fetchalltopics(){

        Requestclassforextraactivity.api_request_fortopics(this, "https://interprac.herokuapp.com/getall", new funcall() {
            @Override
            public void vollycallback(ArrayList<Datafortopics> arrayList) {


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Ieltsactivity.this);
                recyclerView.setAdapter(new Recyclerforactivities(Ieltsactivity.this , arrayList , Ieltsactivity.this));
                 recyclerView.setLayoutManager(linearLayoutManager);

            }
        });

    }


    @Override
    public void gotochat(String room_name) {
        Intent intent = new Intent(this , Groupchatactivity.class);
        intent.putExtra("room",room_name);
        startActivity(intent);
    }
}