package com.surehappiness.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.surehappiness.app.dto.Stamp;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.NotificationCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class NotificationService {

    public static boolean result;

    public static NotificationService instance = new NotificationService();

    private NotificationCall notificationCall = RetrofitNetwork.getInstance().getNotificationCall();

    public boolean notificationOn(final Context context){
        SharedPreferences userInfoPref = context.getSharedPreferences("userInfo", MODE_PRIVATE);
        int userIdx = userInfoPref.getInt("userIdx", 0);

        Call<Stamp> call = notificationCall.notificationOn(userIdx);
        call.enqueue(new Callback<Stamp>() {
            @Override
            public void onResponse(Call<Stamp> call, Response<Stamp> response) {
                if(response.body().getIdx() != 0){
                    result = true;
                    Log.d("result", "## notification " + result);
                } else{
                    result = false;
                    Log.d("result", "## notification " + result);
                }
            }

            @Override
            public void onFailure(Call<Stamp> call, Throwable t) {
                Log.d("result", "## notification failed");
            }
        });
        return result;
    }
}