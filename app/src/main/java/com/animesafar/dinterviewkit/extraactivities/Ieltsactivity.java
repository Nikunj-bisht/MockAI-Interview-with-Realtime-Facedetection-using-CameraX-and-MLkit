package com.animesafar.dinterviewkit.extraactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.animesafar.dinterviewkit.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
   private AlertDialog dialog ;
   private SwipeRefreshLayout s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ieltsactivity);

    alert = new AlertDialog.Builder(this);
        s = findViewById(R.id.swipe);

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
s.setOnRefreshListener(()->{

    fetchalltopics();
    s.setRefreshing(false);
});
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
        else if(item.getItemId() == R.id.findroom){
View view = getLayoutInflater().inflate(R.layout.findalert,null);
EditText editText = view.findViewById(R.id.rm);
            Button button = view.findViewById(R.id.button6);
            button.setOnClickListener((View v)->{
                String typed_text = editText.getText().toString();
                if(typed_text!=null){
                    findtherooms(typed_text);
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
        Retrobuilder.getbuilder().uploadtitle(map)
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


    }

    private void findtherooms(String typed){

        HashMap<String,String> map = new HashMap<>();
        map.put("typed" , typed);
        Retrobuilder.getbuilder().getsearchrooms(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Topicsdata>, ObservableSource<Topicsdata>>() {
                    @Override
                    public ObservableSource<Topicsdata> apply(List<Topicsdata> topicsdata) throws Throwable {
                        Toast.makeText(Ieltsactivity.this,topicsdata.toString(),Toast.LENGTH_LONG).show();


                        return null;
                    }

                }).subscribe(new Observer<Topicsdata>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Topicsdata topicsdata) {

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(Ieltsactivity.this,"error",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onComplete() {

            }
        });
    }


}