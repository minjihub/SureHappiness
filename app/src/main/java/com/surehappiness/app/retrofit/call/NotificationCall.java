package com.surehappiness.app.retrofit.call;

import com.surehappiness.app.dto.Stamp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationCall {

    @GET("/notification/{user_idx}")
    Call<Stamp> notificationOn(@Path("user_idx") int user_idx);
}
