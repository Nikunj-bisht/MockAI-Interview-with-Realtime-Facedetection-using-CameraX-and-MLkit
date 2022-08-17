package com.animesafar.dinterviewkit.extraactivities;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrobuilder {

    public static Topic getbuilder(){



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://interprac.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());

        Retrofit retrofit = builder.build();
        Topic topic = retrofit.create(Topic.class);

        return topic;

    }
}
