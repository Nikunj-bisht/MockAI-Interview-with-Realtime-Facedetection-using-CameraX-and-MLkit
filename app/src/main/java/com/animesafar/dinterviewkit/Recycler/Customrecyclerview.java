package com.animesafar.dinterviewkit.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

import java.util.ArrayList;

public class Customrecyclerview extends RecyclerView.Adapter<Customviewholder> {

Context context;
LayoutInflater layoutInflater;
ArrayList<Jobs> arrayList;
senditem send;

public interface senditem{
    void openquestions(String item);
}

    public Customrecyclerview(Context context, ArrayList<Jobs> arrayList,senditem s){

        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
this.send = s;


    }

    @NonNull
    @Override
    public Customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = layoutInflater.inflate(R.layout.recyclecustomview,parent,false);


          Customviewholder customviewholder = new Customviewholder(view);



        return customviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Customviewholder holder, int position) {

        holder.getTextView1().setText(arrayList.get(position).getTitle());
        holder.getTextview2().setText(arrayList.get(position).getDescription());

        holder.getfloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 send.openquestions(arrayList.get(position).getTitle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
