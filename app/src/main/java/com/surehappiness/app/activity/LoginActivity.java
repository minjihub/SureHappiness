package com.surehappiness.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.surehappiness.app.R;
import com.surehappiness.app.activity.model.UserInfo;
import com.surehappiness.app.databinding.LayoutLoginBinding;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.UserCall;
import com.surehappiness.app.utils.CommonFunction;
import com.surehappiness.app.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LayoutLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_login);
        autoLogin();

        binding.joinBtn.setOnClickListener(this);
        binding.findBtn.setOnClickListener(this);
        binding.loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.joinBtn :
                Intent joinIntent = new Intent(getApplicationContext(), JoinActivity.class);
                joinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(joinIntent);
                break;

            case R.id.findBtn :
                Intent findIntent = new Intent(getApplicationContext(), FindAccount.class);
                startActivity(findIntent);
                break;

            case R.id.loginBtn :
                login();
                break;
        }
    }

    private void autoLogin(){
        NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        SharedPreferences userInfoPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        String autoUserId = userInfoPref.getString("userAccount", null);
        String autoUserPw = userInfoPref.getString("userPw", null);

        if(autoUserId == null || autoUserPw == null){
            return;
        }

        binding.userId.setText(autoUserId);
        binding.userPw.setText(autoUserPw);

        if (!networkInfo.isConnected()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), Navigation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void login(){
        NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        UserCall userCall = RetrofitNetwork.getInstance().getUserCall();

        if(!networkInfo.isConnected()){
            CommonFunction.Companion.showBasicDialog(getApplicationContext(), R.string.network_fail);
            return;
        }

        String id = binding.userId.getText().toString();
        String pw = binding.userPw.getText().toString();

        JsonObject loginInfo = new JsonObject();
        loginInfo.addProperty("account", id);
        loginInfo.addProperty("password", pw);


        Call<UserInfo> call = userCall.userLogin(id, pw);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.code() != 200){
                    CommonFunction.Companion.showBasicDialog(LoginActivity.this, R.string.login_fail);
                    return;
                }

                UserInfo responseUser = response.body();
                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                pref.edit().putInt("userId", responseUser.getId()).apply();
                pref.edit().putString("userAccount", responseUser.getAccount()).apply();
                pref.edit().putString("userPw", responseUser.getPassword()).apply();
                pref.edit().putString("userName", responseUser.getName()).apply();
                pref.edit().putString("userEmail", responseUser.getEmail()).apply();

                Intent intent = new Intent(LoginActivity.this, Navigation.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                CommonFunction.Companion.showBasicDialog(getApplicationContext(), R.string.server_connect_fail);
            }
        });
    }


}