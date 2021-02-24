package com.surehappiness.app.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.surehappiness.app.R;
import com.surehappiness.app.dto.Purpose;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.PurposeCall;
import com.surehappiness.app.utils.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePurpose extends AppCompatActivity {

    private TextView startDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_purpose);

        final NetworkInfo networkInfo = NetworkUtils.Companion.getNetworkInfo(this);
        final PurposeCall purposeCall = RetrofitNetwork.getInstance().getPurposeCall();

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences userInfoPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        final int user_idx = userInfoPref.getInt("userIdx", 0);

        final EditText purpose_name = findViewById(R.id.purpose_name);
        final EditText purpose_memo = findViewById(R.id.purpose_description);
        final TextView notice = findViewById(R.id.notice);

        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateDialog(startDate);
            }
        });

        final TextView endDate = findViewById(R.id.end_date);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDateDialog(endDate);
            }
        });

        final TextView stampCount = findViewById(R.id.stamp_count);
        stampCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountPickerDialog(stampCount);
            }
        });


        FrameLayout createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(purpose_name.getText().toString().equals("")){
                    notice.setText("* 목표명을 입력해주세요.");
                } else if(startDate.getText().toString().equals("")){
                    notice.setText("* 시작일을 입력해주세요.");
                } else if(endDate.getText().toString().equals("")){
                    notice.setText("* 완료일을 입력해주세요.");
                } else if(stampCount.getText().toString().equals("")){
                    notice.setText("* 목표 실천수(스탬프 수)를 입력해주세요.");
                } else{
                    String start_date = startDate.getText().toString();
                    String end_date = endDate.getText().toString();
                    Date StringToStartDate = null;
                    Date StringToEndDate = null;
                    try {
                        StringToStartDate = new SimpleDateFormat("yyyy. MM. dd").parse(start_date);
                        StringToEndDate = new SimpleDateFormat("yyyy. MM. dd").parse(end_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(StringToStartDate.after(StringToEndDate)){
                        notice.setText("* 시작일은 완료일 이전으로 지정해야 합니다.\n  날짜를 다시 지정해주세요.");
                    } else{
                        Purpose requestPurpose = new Purpose();
                        requestPurpose.setUser_idx(user_idx);
                        requestPurpose.setPurposeName(purpose_name.getText().toString());
                        requestPurpose.setPurposeMemo(purpose_memo.getText().toString());
                        requestPurpose.setStartDate(startDate.getHint().toString());
                        requestPurpose.setEndDate(endDate.getHint().toString());
                        requestPurpose.setStampNum(Integer.parseInt(stampCount.getText().toString()));

                        if(networkInfo.isConnected()){
                            Call<Purpose> call = purposeCall.createPurpose(requestPurpose);
                            call.enqueue(new Callback<Purpose>() {
                                @Override
                                public void onResponse(Call<Purpose> call, Response<Purpose> response) {
                                    if(response.body().getIdx() != 0){
                                        Toast.makeText(getApplicationContext(), "목표 생성 완료", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else{
                                        notice.setText("* 목표 정보를 잘못 기입하셨습니다.");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Purpose> call, Throwable t) {
                                    notice.setText("* 서버 연결이 원활하지 않습니다.");
                                }
                            });
                        } else{
                            dialog.setMessage("네트워크 연결 상태를 확인해주세요.").show();
                        }
                    }
                }
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

    private void showStartDateDialog(final TextView textView){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        Calendar date = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();  // 정한 날짜
                newDate.set(year, monthOfYear, dayOfMonth);

                Calendar calendar = Calendar.getInstance(); // 오늘 날짜
                calendar.add(Calendar.DATE, -1);

                if(newDate.getTime().after(calendar.getTime())){
                    textView.setText(new SimpleDateFormat("yyyy. MM. dd", Locale.getDefault()).format(newDate.getTime()));
                    textView.setHint(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.getTime()));
                } else{
                    dialog.setMessage("시작일은 오늘 날짜 이후로 지정해야 합니다.\n\n날짜를 다시 지정해주세요.").show();
                }
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showEndDateDialog(final TextView textView){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        Calendar date = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();  // 정한 날짜
                newDate.set(year, monthOfYear, dayOfMonth);

                Calendar calendar = Calendar.getInstance(); // 오늘 날짜
                calendar.add(Calendar.DATE, -1);

                String start_date = startDate.getText().toString(); // 시작일
                if(!start_date.equals("")){
                    Date StringToDate = null;
                    try {
                        StringToDate = new SimpleDateFormat("yyyy. MM. dd").parse(start_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(newDate.getTime().after(calendar.getTime()) && newDate.getTime().after(StringToDate)){
                        textView.setText(new SimpleDateFormat("yyyy. MM. dd", Locale.getDefault()).format(newDate.getTime()));
                        textView.setHint(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.getTime()));
                    } else{
                        dialog.setMessage("완료일은 시작일 이후로 지정해야 합니다.\n\n날짜를 다시 지정해주세요.").show();
                    }
                } else{
                    if(newDate.getTime().after(calendar.getTime())){
                        textView.setText(new SimpleDateFormat("yyyy. MM. dd", Locale.getDefault()).format(newDate.getTime()));
                        textView.setHint(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate.getTime()));
                    } else{
                        dialog.setMessage("완료일은 오늘 날짜 이후로 지정해야 합니다.\n\n날짜를 다시 지정해주세요.").show();
                    }
                }

            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showCountPickerDialog(final TextView textView){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.count_picker, null);
        dialog.setView(dialogView);
        dialog.setTitle("목표 실천수");
        dialog.setMessage("선택한 개수만큼 스탬프판이 생겨요 :)");

        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);

        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                return;
            }
        });

        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText( String.valueOf(numberPicker.getValue()));
            }
        });


        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}
