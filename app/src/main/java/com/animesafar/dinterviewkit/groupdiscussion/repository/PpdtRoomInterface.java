package com.animesafar.dinterviewkit.groupdiscussion.repository;

import com.animesafar.dinterviewkit.groupdiscussion.PpdtRooms;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface PpdtRoomInterface {

    @GET
            ("/getPpdtRooms")
    Call<List<PpdtRooms>> getPpdtRooms();

}
