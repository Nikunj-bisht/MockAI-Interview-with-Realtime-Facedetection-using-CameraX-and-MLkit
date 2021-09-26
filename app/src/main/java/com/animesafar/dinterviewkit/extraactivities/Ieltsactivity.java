package com.animesafar.dinterviewkit.extraactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.animesafar.dinterviewkit.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ieltsactivity extends AppCompatActivity implements gotochatinterface {

    private RecyclerView recyclerView;
  private   AlertDialog.Builder alert;
   private AlertDialog dialog;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.ieltsmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.addnew){

            alert = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.addalert,null);
            EditText title = view.findViewById(R.id.rt),code = view.findViewById(R.id.rc);
            ProgressBar progressBar = view.findViewById(R.id.progressBar4);
            view.findViewById(R.id.button5).setOnClickListener((View v)->{
                String t = title.getText().toString(),c = code.getText().toString();
                if(t!=null && c!=null){
progressBar.setVisibility(View.VISIBLE);
                    savetodatabase(t,c);

                }else{

                }
            });
            alert.setView(view);

             dialog = alert.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    private void savetodatabase(String t  ,String c) {

        HashMap<String, String> map = new HashMap<>();
        map.put("tit",t);
        map.put("code",c);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://interprac.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
                Topic topic = retrofit.create(Topic.class);


        topic.uploadtitle(map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
            @Override
            public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Throwable {
                dialog.dismiss();
   Toast.makeText(Ieltsactivity.this,responseBody.toString(),Toast.LENGTH_LONG).show();
                return null;
            }
        }).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.println(response.body().toString());
//                Toast.makeText(Ieltsactivity.this,"Topic created",Toast.LENGTH_LONG).show();
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(Ieltsactivity.this,"Error try again!",Toast.LENGTH_LONG).show();
//
//            }
//        });



    }
}