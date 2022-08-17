package com.animesafar.dinterviewkit.extraactivities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchatactivity);

        floatingActionButton = findViewById(R.id.floa);
        editText = findViewById(R.id.messaget);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText,InputMethodManager.SHOW_FORCED);

        registertypeevent();
        floatingActionButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.chatrecycle);
        arrayList = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.actionedit,null);
        textView = view.findViewById(R.id.textView14);
      //  actionBar.setCustomView(view);

        try {
            room_name = getIntent().getStringExtra("room");
            IO.Options options = new IO.Options();
            options.path = "/chat";
            socket = IO.socket("https://interprac.herokuapp.com",options);
            socket.connect();
            eventsforsocket();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("room_name",room_name);
        socket.emit("create",jsonObject);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new Chatrecycler(this,arrayList));
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }catch (JSONException jsonException){
            jsonException.printStackTrace();
        }

    }

    private void registertypeevent() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              //  socket.emit("type",room_name);

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });



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

socket.on("typing", new Emitter.Listener() {
    @Override
    public void call(Object... args) {

if(args[0]!=null){
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
          //  Toast.makeText(Groupchatactivity.this , "typi",Toast.LENGTH_LONG).show();
            textView.setText("Typing..");
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
            try{

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("room_name",room_name);
                jsonObject.put("message" , messa);
                jsonObject.put(("user_name"),"Nikunj");
                socket.emit("mssg" , jsonObject);

            }catch (Exception e){

            }
        }
    }
});
    }

    @Synchronized
    protected void update(String messa){
        try {
            JSONObject jsonObject = new JSONObject(messa);
            Message message = new Message(jsonObject.getString("mess") , jsonObject.getString("user"));
            arrayList.add(message);
            Chatrecycler chatrecycler = (Chatrecycler) recyclerView.getAdapter();
            chatrecycler.addnewmessage(arrayList);
            chatrecycler.notifyDataSetChanged();
            recyclerView.scrollToPosition(arrayList.size()-1);

        }catch (Exception e){

        }
}

}