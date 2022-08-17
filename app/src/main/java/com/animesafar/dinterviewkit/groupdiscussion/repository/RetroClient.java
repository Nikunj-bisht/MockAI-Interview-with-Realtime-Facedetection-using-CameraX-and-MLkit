package com.animesafar.dinterviewkit.groupdiscussion.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private PpdtRoomInterface ppdtRoomInterface;

    private static RetroClient instance = null;

    private RetroClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ppdtserver.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ppdtRoomInterface = retrofit.create(PpdtRoomInterface.class);
    }

    public static synchronized RetroClient getInstance() {
        if (instance == null) {
            instance = new RetroClient();
        }
        return instance;
    }

    public PpdtRoomInterface getMyApi() {
        return ppdtRoomInterface;
    }



}
