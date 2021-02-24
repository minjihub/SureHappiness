package com.surehappiness.app.retrofit;

import com.surehappiness.app.retrofit.call.NotificationCall;
import com.surehappiness.app.retrofit.call.PurposeCall;
import com.surehappiness.app.retrofit.call.RankCall;
import com.surehappiness.app.retrofit.call.StampCall;
import com.surehappiness.app.retrofit.call.UserCall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetwork {

    private Retrofit retrofit;
    private UserCall userCall;
    private PurposeCall purposeCall;
    private StampCall stampCall;
    private RankCall rankCall;
    private NotificationCall notificationCall;

    public static RetrofitNetwork instance = new RetrofitNetwork();

    public RetrofitNetwork(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://14.129.18.182:8181")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userCall = retrofit.create(UserCall.class);
        purposeCall = retrofit.create(PurposeCall.class);
        stampCall = retrofit.create(StampCall.class);
        rankCall = retrofit.create(RankCall.class);
        notificationCall = retrofit.create(NotificationCall.class);
    }

    public static RetrofitNetwork getInstance(){
        return instance;
    }

    public UserCall getUserCall(){
        return userCall;
    }

    public PurposeCall getPurposeCall(){
        return purposeCall;
    }

    public StampCall getStampCall(){
        return stampCall;
    }

    public RankCall getRankCall(){
        return rankCall;
    }

    public NotificationCall getNotificationCall(){
        return notificationCall;
    }
}