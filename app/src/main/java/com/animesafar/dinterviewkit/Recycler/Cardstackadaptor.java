package com.animesafar.dinterviewkit.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animesafar.dinterviewkit.R;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;

public class Cardstackadaptor extends RecyclerView.Adapter<Cardstackviewholder> {


    ArrayList<Jobs> arrayList;

    Context context;
    jump j;
    CardStackView cardStackView;
    public Cardstackadaptor(Context context , ArrayList<Jobs> arr , jump ju , CardStackView cardStackView){

        this.arrayList = arr;
this.context = context;
this.j = ju;
this.cardStackView = cardStackView;
    }

   public interface jump{

        public void jumpnow();

   }

    @NonNull
    @Override
    public Cardstackviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cards,parent,false);
        Cardstackviewholder cardstackviewholder =  new Cardstackviewholder(view);

        return cardstackviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Cardstackviewholder holder, int position) {
holder.getTextView().setText("Question "+position);
        holder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String des =   holder.getMultiAutoCompleteTextView().getText().toString();
              if(!des.equals("")){
                  Jobs jobs = new Jobs(null,des);
arrayList.add(jobs);
cardStackView.swipe();
              }else{
                  Toast.makeText(context,"Enter question first",Toast.LENGTH_SHORT).show();
              }

            }
        });
holder.getBt().setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        j.jumpnow();
    }
});
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
