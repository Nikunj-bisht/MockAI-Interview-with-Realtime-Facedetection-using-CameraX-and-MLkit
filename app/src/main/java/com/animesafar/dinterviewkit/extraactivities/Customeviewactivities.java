package com.animesafar.dinterviewkit.extraactivities;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

public class Customeviewactivities extends RecyclerView.ViewHolder {

private TextView textView1 , textView2;
private Button button;

    public TextView getTextView1() {
        return textView1;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public Button getButton() {
        return button;
    }

    public Customeviewactivities(@NonNull View itemView) {
        super(itemView);
this.textView1 = itemView.findViewById(R.id.textView10);
this.textView2 = itemView.findViewById(R.id.textView11);
this.button = itemView.findViewById(R.id.button4);

    }
}
