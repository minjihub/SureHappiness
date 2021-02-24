package com.surehappiness.app.retrofit.call;

import com.surehappiness.app.dto.Rank;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RankCall {

    @GET("/rank/{userId}")
    Call<List<Rank>> getRank(@Path("userId") String userId);
}
