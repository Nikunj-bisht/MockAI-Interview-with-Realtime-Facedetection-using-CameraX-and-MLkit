package com.animesafar.dinterviewkit.Recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Customviewholder extends RecyclerView.ViewHolder {

TextView textView1;
TextView textview2;
FloatingActionButton floatingActionButton;


    public Customviewholder(@NonNull View itemView) {
        super(itemView);

        textView1 = itemView.findViewById(R.id.textView4);

        textview2 = itemView.findViewById(R.id.textView5);
floatingActionButton = itemView.findViewById(R.id.floatingActionButton3);
    }

    public TextView getTextView1(){

        return this.textView1;

    }

    public TextView getTextview2(){

        return this.textview2;

    }

    public FloatingActionButton getfloatingActionButton(){

        return this.floatingActionButton;

    }


}
