package com.surehappiness.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.surehappiness.app.R;
import com.surehappiness.app.dto.User;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.UserCall;
import com.surehappiness.app.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_modify);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        final UserCall userCall = RetrofitNetwork.getInstance().getUserCall();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(UserUpdateActivity.this);

        SharedPreferences userInfoPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        final SharedPreferences.Editor userInfoEditor = userInfoPref.edit();

        final EditText userName = findViewById(R.id.user_name);
        final TextView userId = findViewById(R.id.user_id);
        final EditText userEmail = findViewById(R.id.user_email);
        final TextView notice = findViewById(R.id.notice);

        final int getUserIdx = userInfoPref.getInt("userIdx", 0);
        String getUserName = userInfoPref.getString("userName", null);
        final String getUserId = userInfoPref.getString("userId", null);
        final String getUserPw = userInfoPref.getString("userPw", null);
        String getUserEmail = userInfoPref.getString("userEmail", null);

        userName.setText(getUserName);
        userId.setText(getUserId);
        userEmail.setText(getUserEmail);

        userName.setSelection(userName.length());
        userEmail.setSelection(userEmail.length());

        TextView pwCheckBtn = findViewById(R.id.user_pw);
        pwCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PwCheckAndUpdateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        FrameLayout userDeleteBtn = findViewById(R.id.userDeleteBtn);
        userDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PwCheckAndDeleteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        FrameLayout saveBtn = findViewById(R.id.saveBtn);
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final User requestUser = new User();
//                requestUser.setUserId(userId.getText().toString());
//                requestUser.setUserPw(getUserPw);
//                requestUser.setUserName(userName.getText().toString());
//                requestUser.setUserEmail(userEmail.getText().toString());
//
//                if(!userEmail.getText().toString().contains("@")){
//                    notice.setText("* 이메일 주소가 올바르지 않습니다.");
//                } else{
//                    if(getUserIdx != 0){
//                        if(networkInfo.isConnected()){
//                            Call<User> call = userCall.userInfoUpdate(getUserIdx, requestUser);
//                            call.enqueue(new Callback<User>() {
//                                @Override
//                                public void onResponse(Call<User> call, Response<User> response) {
//                                    User responseUser = response.body();
//                                    if(responseUser.getIdx() == 0 && responseUser.getUserId().equals("overlapEmail")){
//                                        notice.setText("* 이미 존재하는 이메일 주소입니다.");
//                                    } else if(responseUser.getIdx() == 0){
//                                        notice.setText("* 서버 연결이 원활하지 않습니다.");
//                                    } else{
//                                        // 사용자 정보 수정
//                                        userInfoEditor.putString("userName", responseUser.getUserName());
//                                        userInfoEditor.putString("userEmail", responseUser.getUserEmail());
//                                        userInfoEditor.apply();
//
//                                        finish();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<User> call, Throwable t) {
//                                    notice.setText("* 서버 연결이 원활하지 않습니다.");
//                                }
//                            });
//                        } else{
//                            dialog.setMessage("네트워크 연결 상태를 확인해주세요.").show();
//                        }
//                    }
//                }
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}