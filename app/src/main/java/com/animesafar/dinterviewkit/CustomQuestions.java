package com.animesafar.dinterviewkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.animesafar.dinterviewkit.Recycler.Cardstackadaptor;
import com.animesafar.dinterviewkit.Recycler.Jobs;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;

public class CustomQuestions extends AppCompatActivity  implements  Cardstackadaptor.jump
{

    CardStackView cardStackView;
ArrayList<Jobs> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_questions);
        getSupportActionBar().hide();

        cardStackView = findViewById(R.id.cardstack);

        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this);
        cardStackLayoutManager.setSwipeThreshold(0.1f);
          cardStackView.setLayoutManager(cardStackLayoutManager);
          arrayList = new ArrayList<Jobs>();

          cardStackView.setAdapter(new Cardstackadaptor(this,arrayList,this::jumpnow,cardStackView));

    }

    @Override
    public void jumpnow() {

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("questions",arrayList);
startActivity(intent);
    }
}