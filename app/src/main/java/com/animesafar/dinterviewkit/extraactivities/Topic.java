package com.animesafar.dinterviewkit.extraactivities;


import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface Topic {

    @FormUrlEncoded
     @POST("/save")
    Observable<ResponseBody> uploadtitle(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/getsearch")
    Observable<List<Topicsdata>> getsearchrooms(@FieldMap Map<String,String> map);


}
