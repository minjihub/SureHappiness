package com.surehappiness.app.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.surehappiness.app.R;
import com.surehappiness.app.activity.model.PurposeInfo;
import com.surehappiness.app.dto.Purpose;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.PurposeCall;
import com.surehappiness.app.utils.NetworkUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurposeModify extends AppCompatActivity {

    private EditText purposeName;
    private EditText memo;
    private TextView endDate;
    private TextView notice;

    private int purposeIdx;
    private String purposeState;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_purpose_modify);

        purposeName = findViewById(R.id.purpose_name);
        memo = findViewById(R.id.purpose_description);
        endDate = findViewById(R.id.end_date);
        TextView startDate = findViewById(R.id.start_date);
        TextView stampNum = findViewById(R.id.stamp_count);

        notice = findViewById(R.id.notice);
        FrameLayout saveBtn = findViewById(R.id.saveBtn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final PurposeInfo purpose = intent.getParcelableExtra("purposeInfo");

        purposeIdx = purpose.getIdx();
        purposeState = purpose.getPurposeState();

        purposeName.setText(purpose.getName());
        memo.setText(purpose.getMemo());
        startDate.setText(purpose.getStartDate());
        endDate.setText(purpose.getEndDate());
        stampNum.setText(Integer.toString(purpose.getStampNum()));

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(purpose.getEndDate());
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePurpose(purposeName.getText().toString(), memo.getText().toString(), endDate.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updatePurpose(String purposeName, String memo, String endDate){
        NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        PurposeCall purposeCall = RetrofitNetwork.getInstance().getPurposeCall();

        if(networkInfo.isConnected()){
            Purpose requestPurpose = new Purpose();
            if(!purposeName.equals("")){
                requestPurpose.setPurposeName(purposeName);
            }
            if(!memo.equals("")){
                requestPurpose.setPurposeMemo(memo);
            }
            requestPurpose.setEndDate(endDate);

            Call<Purpose> call = purposeCall.updatePurpose(purposeIdx, requestPurpose);
            call.enqueue(new Callback<Purpose>() {
                @Override
                public void onResponse(Call<Purpose> call, Response<Purpose> response) {
                    Purpose responsePurpose = response.body();

                    if(responsePurpose.getIdx() != 0){
                        Intent sendIntent = new Intent(getApplicationContext(), NewStampActivity.class);

                        sendIntent.putExtra("purposeIdx", purposeIdx);
                        sendIntent.putExtra("purposeState", purposeState);

                        Toast.makeText(getApplicationContext(), "목표 수정 완료", Toast.LENGTH_SHORT).show();

                        startActivity(sendIntent);
                        finish();
                    } else{
                        notice.setText("* 목표 수정 실패!");
                    }
                }

                @Override
                public void onFailure(Call<Purpose> call, Throwable t) {
                    notice.setText("* 서버 연결이 원활하지 않습니다.");
                }
            });
        } else{
            showNoticeDialog("네트워크 연결 상태를 확인해주세요.");
        }
    }


    private void showDateDialog(String endDateStr){
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(endDateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);

                endDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.getTime()));
            }
        }, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }

    private void showNoticeDialog(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setMessage(message);

        dialog.show();
    }

}

