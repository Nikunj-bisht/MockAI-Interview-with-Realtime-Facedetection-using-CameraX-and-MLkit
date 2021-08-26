package com.animesafar.dinterviewkit.Recycler;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.animesafar.dinterviewkit.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Gridviewholder extends RecyclerView.ViewHolder {

private TextView textView;
private ImageView imageView;

public Gridviewholder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView9);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
