package com.animesafar.dinterviewkit.groupdiscussion;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

public class PpdtRoomView extends RecyclerView.ViewHolder {

    private TextView textView;
    private TextView dateTextView;
    private ImageView imageView;
    private View view;

    public PpdtRoomView(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView12);
        dateTextView = itemView.findViewById(R.id.textView15);
        view = itemView.findViewById(R.id.roomId);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public void setDateTextView(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
