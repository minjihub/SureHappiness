package com.surehappiness.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.surehappiness.app.service.MyService;
import com.surehappiness.app.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PwCheckAndDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pw_check);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        final UserCall userCall = RetrofitNetwork.getInstance().getUserCall();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(PwCheckAndDeleteActivity.this);

        final SharedPreferences userInfoPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        final SharedPreferences.Editor userInfoEditor = userInfoPref.edit();
        SharedPreferences notificationPref = getSharedPreferences("notification", MODE_PRIVATE);
        final SharedPreferences.Editor notificationEditor = notificationPref.edit();

        TextView DeleteOrUpdate = findViewById(R.id.DeleteOrUpdate);
        DeleteOrUpdate.setText("회원 탈퇴를 위해 현재 비밀번호를 확인해주세요.");

        final TextView userId = findViewById(R.id.user_id);
        final EditText userPw = findViewById(R.id.user_pw);
        final TextView notice = findViewById(R.id.notice);

        final String getUserId = userInfoPref.getString("userId", null);
        userId.setText(getUserId);

        FrameLayout nextBtn = findViewById(R.id.nextBtn);
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(getUserId != null){
//                    final User requestUser = new User();
//                    requestUser.setUserId(userId.getText().toString());
//                    requestUser.setUserPw(userPw.getText().toString());
//
//                    if (networkInfo.isConnected()) {
//                        Call<User> call = userCall.userLogin(requestUser);
//                        call.enqueue(new Callback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//                                if (response.body().getIdx() != 0) {
//                                    // 아이디, 비밀번호 정보 일치하면 탈퇴 진행
//                                    int userIdx = userInfoPref.getInt("userIdx", 0);
//
//                                    Call<User> call2 = userCall.userInfoDelete(userIdx);
//                                    call2.enqueue(new Callback<User>() {
//                                        @Override
//                                        public void onResponse(Call<User> call, Response<User> response) {
//                                            User responseUser = response.body();
//                                            if (responseUser.getUserId().equals("delete")) {
//                                                notificationEditor.putBoolean("switchOnOff", false);
//                                                notificationEditor.apply();
//
//                                                Intent intent = new Intent(getApplicationContext(), MyService.class);
//                                                stopService(intent);
//
//                                                // 사용자 정보 삭제
//                                                userInfoEditor.clear();
//                                                userInfoEditor.apply();
//
//                                                // notification 정보 삭제
//                                                notificationEditor.clear();
//                                                notificationEditor.apply();
//
//                                                dialog
//                                                        .setMessage("회원 탈퇴가 완료되었습니다.")
//                                                        .setCancelable(false)
//                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                dialog.dismiss();
//                                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                                startActivity(intent);
//                                                                finish();
//                                                            }
//                                                        }).show();
//                                            } else {
//                                                notice.setText("* 비밀번호가 일치하지 않습니다.");
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<User> call, Throwable t) {
//                                            notice.setText("* 서버 연결이 원활하지 않습니다.");
//                                        }
//                                    });
//                                } else {
//                                    notice.setText("* 비밀번호가 일치하지 않습니다.");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//                                notice.setText("* 서버 연결이 원활하지 않습니다.");
//                            }
//                        });
//                    } else{
//                        dialog.setMessage("네트워크 연결 상태를 확인해주세요.").show();
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