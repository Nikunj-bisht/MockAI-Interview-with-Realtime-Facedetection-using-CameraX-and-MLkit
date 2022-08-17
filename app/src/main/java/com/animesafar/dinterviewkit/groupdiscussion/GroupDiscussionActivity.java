package com.animesafar.dinterviewkit.groupdiscussion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.animesafar.dinterviewkit.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GroupDiscussionActivity extends AppCompatActivity {

    MenuItem s;
    Socket socket;
    Timer timer;
    String room_name;
    int time = 20;
    ImageView imageView;
    String role;
    FloatingActionButton floatingActionButton;
    BottomSheetDialog bottomSheetDialog;
    String typedText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_discussion);

        imageView = findViewById(R.id.imageView2);
//        Bitmap bitmap = getIntent().getParcelableExtra("bitImage");
        role = getIntent().getStringExtra("role");
        byte[] byteArray = getIntent().getByteArrayExtra("bitImage");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);
        bottomSheetDialog = new BottomSheetDialog(this);
        floatingActionButton = findViewById(R.id.floatingActionButton4);
        bottomSheetDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.writingbox,null));
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED) ;

        EditText editText = LayoutInflater.from(this).inflate(R.layout.writingbox,null).findViewById(R.id.editTextTextMultiLine);
        editText.setText(typedText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               typedText=charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });
        try {
            IO.Options options = new IO.Options();
            options.path = "/gd";
            socket = IO.socket("https://interprac.herokuapp.com", options);
            socket.connect();
            eventsForSockets();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("room_name","room_name");
            socket.emit("create",jsonObject);
        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void eventsForSockets() {

        socket.on("count", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(args.toString()+" time");
                        s.setTitle(String.valueOf(args[0]));
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gdmenu, menu);
        s = menu.findItem(R.id.startgd);
        if (role.equals("user")){
            s.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.startgd) {

            try {

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("room_name", "room_name");
                            jsonObject.put("countTimer", time);
                            sendTimerMessage(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

time--;
                     if (time == 0){
                         timer.cancel();
//                         startGd();
                     }
                    }
                }, 0, 2000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return super.onOptionsItemSelected(item);
    }

    private void startGd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("room_name",room_name);
                    socket.emit("showimage",jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void sendTimerMessage(JSONObject jsonObject) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                socket.emit("start", jsonObject);
            }
        });
    }
}