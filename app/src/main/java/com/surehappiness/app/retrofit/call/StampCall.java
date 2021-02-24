package com.surehappiness.app.retrofit.call;

import com.surehappiness.app.dto.Stamp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StampCall {

    @GET("/stamp/{purpose_idx}")
    Call<List<Stamp>> getStamp(@Path("purpose_idx") int purpose_idx);

    @POST("/stamp")
    Call<List<Stamp>> createStamp(@Body Stamp stamp);

    @PUT("/stamp/{idx}")
    Call<Stamp> updateDateStamp(@Path("idx") int idx, @Body Stamp stamp);

    @DELETE("/stamp/{idx}")
    Call<Stamp> deleteStamp(@Path("idx") int idx);
}