package com.animesafar.dinterviewkit.extraactivities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.animesafar.dinterviewkit.R;

public class Customviewholderforchat extends RecyclerView.ViewHolder
{
    private TextView textView , textView2;

    public TextView getTextView() {
        return textView;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public Customviewholderforchat(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView12);
        textView2 = itemView.findViewById(R.id.textView13);


    }
}
