package com.animesafar.dinterviewkit.extraactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import kotlin.jvm.Synchronized;

public class Groupchatactivity extends AppCompatActivity implements View.OnClickListener{

Socket socket;
String room_name;
FloatingActionButton floatingActionButton;
EditText editText;
RecyclerView recyclerView;
ArrayList<Message> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchatactivity);
        floatingActionButton = findViewById(R.id.floa);
        editText = findViewById(R.id.messaget);
        floatingActionButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.chatrecycle);
        arrayList = new ArrayList<>();
        try {
            room_name = getIntent().getStringExtra("room");
            socket = IO.socket("https://interprac.herokuapp.com");
            socket.connect();

            eventsforsocket();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("room_name",room_name);
        socket.emit("create",jsonObject);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Chatrecycler(this,arrayList));
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }catch (JSONException jsonException){
            jsonException.printStackTrace();
        }

    }

    private void eventsforsocket() {

        socket.on("members", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if(args[0]!=null){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Groupchatactivity.this , args[0].toString() , Toast.LENGTH_LONG).show();

                        }
                    });
                    }
                }
            }
        );
socket.on("getmsg", new Emitter.Listener() {
    @Override
    public void call(Object... args) {

        if(args[0]!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    update(args[0].toString());
                }
            });
        }

    }
});

    }

    @Override
    public void onClick(View view) {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        if(editText.getText() != null){
            String messa = editText.getText().toString();
            update(messa);

            socket.emit("mssg" , messa);

        }

    }
});

    }

    @Synchronized
    protected void update(String messa){

        Message message = new Message(messa);
        arrayList.add(message);
        Chatrecycler chatrecycler = (Chatrecycler) recyclerView.getAdapter();
        chatrecycler.addnewmessage(arrayList);
        chatrecycler.notifyDataSetChanged();
    }

}