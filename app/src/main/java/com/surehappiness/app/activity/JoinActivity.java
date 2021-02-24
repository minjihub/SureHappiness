package com.surehappiness.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.surehappiness.app.R;
import com.surehappiness.app.activity.model.UserInfo;
import com.surehappiness.app.databinding.LayoutJoinBinding;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.UserCall;
import com.surehappiness.app.utils.CommonFunction;
import com.surehappiness.app.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    LayoutJoinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_join);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FrameLayout joinBtn = findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RxBinding 으로 변경?

                if (binding.userId.getText().toString().equals("")) {
                    binding.notice.setText("* 아이디를 입력하세요.");
                    return;
                } else if (binding.userName.getText().toString().equals("")) {
                    binding.notice.setText("* 이름을 입력하세요.");
                    return;
                } else if (binding.userPw.getText().toString().equals("")) {
                    binding.notice.setText("* 비밀번호를 입력하세요.");
                    return;
                } else if (binding.userEmail.getText().toString().equals("")) {
                    binding.notice.setText("* 이메일 주소를 입력하세요.");
                    return;
                } else if (!binding.userEmail.getText().toString().contains("@")) {
                    binding.notice.setText("* 이메일 형식이 올바르지 않습니다.");
                    return;
                } else if (!binding.userPw.getText().toString().equals(binding.userPw2.getText().toString())) {
                    binding.notice.setText("* 비밀번호가 일치하지 않습니다.");
                    return;
                }

                joinUser();
            }
        });

    }

    private void joinUser() {
        NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        UserCall userCall = RetrofitNetwork.getInstance().getUserCall();

        if(!networkInfo.isConnected()){
            CommonFunction.Companion.showBasicDialog(getApplicationContext(), R.string.network_fail);
            return;
        }

        JsonObject user = new JsonObject();
        user.addProperty("account", binding.userId.getText().toString());
        user.addProperty("password", binding.userPw.getText().toString());
        user.addProperty("name", binding.userName.getText().toString());
        user.addProperty("email", binding.userEmail.getText().toString());

        Call<JsonObject> call = userCall.userJoin(user);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject responseUser = response.body();

                if(response.code() != 200){
                    String errCode = CommonFunction.Companion.errJsonParsing(response.errorBody());

                    if(errCode == null){
                        CommonFunction.Companion.showBasicDialog(JoinActivity.this, R.string.error);
                        return;
                    }

                    switch (errCode){
                        case "EU001" :
                            binding.notice.setText("* 존재하는 아이디입니다.");
                            break;

                        case "EU002" :
                            binding.notice.setText("* 이미 가입된 이메일입니다.");
                            break;

                        default :
                            CommonFunction.Companion.showBasicDialog(JoinActivity.this, R.string.error);
                            break;
                    }
                    return;
                }

                String msg = responseUser.get("name").getAsString() + " 님, 가입을 환영합니다!";
                Toast.makeText(JoinActivity.this, msg, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CommonFunction.Companion.showBasicDialog(JoinActivity.this, R.string.server_connect_fail);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}