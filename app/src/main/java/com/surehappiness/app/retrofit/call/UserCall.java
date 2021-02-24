package com.surehappiness.app.retrofit.call;

import com.google.gson.JsonObject;
import com.surehappiness.app.activity.model.UserInfo;
import com.surehappiness.app.dto.User;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserCall {

    @POST("/user")
    @Headers( "Content-Type: application/json; charset=utf-8")
    Call<JsonObject> userJoin(@Body JsonObject user);

    @GET("/user")
    Call<UserInfo> userLogin(@Query("account") String account, @Query("password") String password);

    @PUT("/user/{idx}")
    Call<UserInfo> userInfoUpdate(@Body JsonObject userUpdateInfo);

    @DELETE("/user/{idx}")
    Call<User> userInfoDelete(@Path("idx") int idx);

    @GET("/find/account")
    Call<JsonObject> findUserId(@Query("name") String name, @Query("email") String email);

    @GET("/find/pw")
    Call<Boolean> findUserPw(@Query("account") String account, @Query("name") String name, @Query("email") String email);
}