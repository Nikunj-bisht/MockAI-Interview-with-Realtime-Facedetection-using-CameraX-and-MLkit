package com.animesafar.dinterviewkit.extraactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

import java.util.ArrayList;

public class Recyclerforactivities extends RecyclerView.Adapter<Customeviewactivities> {

    private Context context;
    private ArrayList<Datafortopics> arrayList;
    private LayoutInflater layoutInflater;
private gotochatinterface go;

     Recyclerforactivities(Context context , ArrayList<Datafortopics> arrayList , gotochatinterface go){
         this.context = context;
         this.arrayList = arrayList;
         this.layoutInflater = LayoutInflater.from(context);
         this.go = go;
     }

    @NonNull
    @Override
    public Customeviewactivities onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = layoutInflater.inflate(R.layout.customforact , parent , false);
        return new Customeviewactivities(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Customeviewactivities holder, int position) {
         final int i = position;
holder.getTextView1().setText(arrayList.get(position).getTopic());
holder.getTextView2().setText(arrayList.get(position).getCode());
holder.getButton().setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
go.gotochat(arrayList.get(i).getCode());
    }
});
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
