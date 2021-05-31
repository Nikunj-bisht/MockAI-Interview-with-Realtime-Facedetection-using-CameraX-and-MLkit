package com.animesafar.dinterviewkit.Recycler;

import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

public class Cardstackviewholder extends RecyclerView.ViewHolder {

    TextView textView;
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    Button button;
Button bt;

    public Button getBt() {
        return bt;
    }

    public void setBt(Button bt) {
        this.bt = bt;
    }

    public Cardstackviewholder(View view){

        super(view);
textView = view.findViewById(R.id.custques);
multiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView);
button = view.findViewById(R.id.button2);
bt = view.findViewById(R.id.button3);

    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public MultiAutoCompleteTextView getMultiAutoCompleteTextView() {
        return multiAutoCompleteTextView;
    }

    public void setMultiAutoCompleteTextView(MultiAutoCompleteTextView multiAutoCompleteTextView) {
        this.multiAutoCompleteTextView = multiAutoCompleteTextView;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
