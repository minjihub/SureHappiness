package com.surehappiness.app.retrofit.call;

import com.surehappiness.app.activity.model.NewPurposeInfo;
import com.surehappiness.app.dto.Purpose;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PurposeCall {

    @POST("/purpose")
    Call<Purpose> createPurpose(@Body Purpose purpose);

    @GET("/purpose/{idx}")
    Call<Purpose> getPurpose(@Path("idx") int idx);

    @GET("/purpose/{status}/{userId}")
    Call<List<NewPurposeInfo>> getPurposeList(@Path("status") String status, @Path("userId") int userId);

    @GET("/purpose/{user_idx}/success")
    Call<List<Purpose>> getPurposeSuccessList(@Path("user_idx") int user_idx);

    @POST("/purpose/{user_idx}/search")
    Call<List<Purpose>> searchPurpose(@Path("user_idx") int user_idx, @Body Purpose purpose);

    @PUT("/purpose/{idx}")
    Call<Purpose> updatePurpose(@Path("idx") int idx, @Body Purpose purpose);

    @DELETE("/purpose/{idx}")
    Call<Purpose> deletePurpose(@Path("idx") int idx);
}