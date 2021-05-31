package com.animesafar.dinterviewkit.datapackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.animesafar.dinterviewkit.Questionslist;
import com.animesafar.dinterviewkit.R;
import com.animesafar.dinterviewkit.Recycler.Jobs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Customadaptor extends BaseAdapter {

    LayoutInflater layoutInflater;
 Context context;

 quesclicked qs;

 ArrayList<Jobs> arrayList;
 public interface quesclicked{

     void clicked(int i);

 }

    public Customadaptor(quesclicked q, Context context , ArrayList<Jobs> arrayList){

     this.qs = q;
     this.arrayList = arrayList;
layoutInflater= LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {

        return 0;

// app id   ca-app-pub-8549163288628134~6914113708

 //  banner id     ca-app-pub-8549163288628134/6530970324

 }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.customsingleview ,null );

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(arrayList.get(i).getDescription());
        textView.setTextColor(Color.BLACK);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);



view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        floatingActionButton.setImageResource(R.drawable.ic_baseline_done_all_24);

        qs.clicked(i);

    }
});

        return view;
    }
}
