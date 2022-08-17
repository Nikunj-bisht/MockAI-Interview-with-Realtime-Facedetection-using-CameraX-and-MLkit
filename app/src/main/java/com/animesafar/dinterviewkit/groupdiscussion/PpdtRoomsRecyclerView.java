package com.animesafar.dinterviewkit.groupdiscussion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;

import java.util.ArrayList;
import java.util.List;

interface joinRoom {
    void joinRoom(String roomName,String imageUrl);
}

public class PpdtRoomsRecyclerView extends RecyclerView.Adapter<PpdtRoomView> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<PpdtRooms> arrayList;
    private joinRoom joinRoom;
    public PpdtRoomsRecyclerView(Context context,List<PpdtRooms> arrayList,joinRoom joinRoom){
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.joinRoom = joinRoom;
    }



    @NonNull
    @Override
    public PpdtRoomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.ppdtlist,parent,false);
        return new PpdtRoomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PpdtRoomView holder, int position) {
        final int i = position;
        holder.getTextView().setText(arrayList.get(position).getTitle());
        holder.getDateTextView().setText(arrayList.get(position).getDate().toString());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinRoom.joinRoom(arrayList.get(i).getTitle(),arrayList.get(i).getImageName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
