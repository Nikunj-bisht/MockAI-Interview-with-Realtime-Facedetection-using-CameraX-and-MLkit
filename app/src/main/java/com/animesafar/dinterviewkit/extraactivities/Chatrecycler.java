package com.animesafar.dinterviewkit.extraactivities;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

import java.util.ArrayList;

public class Chatrecycler extends RecyclerView.Adapter<Customviewholderforchat> {

    private Context context;
    private ArrayList<Message> messages;
    private LayoutInflater layoutInflater;
    Chatrecycler(Context context , ArrayList<Message> arrayList){
this.context = context;
this.messages = arrayList;
this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Customviewholderforchat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = layoutInflater.inflate(R.layout.singlemessage ,  parent , false);
        return new Customviewholderforchat(view);
    }

    protected void addnewmessage(ArrayList<Message> arrayList){
        this.messages = arrayList;

    }

    @Override
    public void onBindViewHolder(@NonNull Customviewholderforchat holder, int position) {

//holder.getTextView().setGravity(Gravity.RIGHT);
//holder.getTextView2().setGravity(Gravity.RIGHT);
        holder.getTextView2().setText(messages.get(position).getMessage());
        holder.getTextView().setText(messages.get(position).getUser());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
