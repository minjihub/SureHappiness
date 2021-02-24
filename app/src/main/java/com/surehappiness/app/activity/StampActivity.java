package com.surehappiness.app.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.surehappiness.app.R;
import com.surehappiness.app.dto.Purpose;
import com.surehappiness.app.dto.Stamp;
import com.surehappiness.app.retrofit.RetrofitNetwork;
import com.surehappiness.app.retrofit.call.PurposeCall;
import com.surehappiness.app.retrofit.call.StampCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StampActivity extends AppCompatActivity {

    private TextView purpose_name;
    private TextView purpose_memo;
    private TextView start_date;
    private TextView end_date;
    private FrameLayout stampBtn;
    private TextView buttonText;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private int purposeIdx;
    private String purposeState;

    private List<Stamp> stamp;

    private PurposeCall purposeCall = RetrofitNetwork.getInstance().getPurposeCall();
    private StampCall stampCall = RetrofitNetwork.getInstance().getStampCall();

    private List<Stamp> offStampList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stamp_board);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_veiw);
        layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);


        purpose_name = findViewById(R.id.purpose_name);
        purpose_memo = findViewById(R.id.purpose_memo);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        stampBtn = findViewById(R.id.stampBtn);
        buttonText = findViewById(R.id.button_text);

        Intent intent = getIntent();
        purposeIdx = intent.getExtras().getInt("purposeIdx");
        purposeState = intent.getExtras().getString("purposeState");

        offStampList = new ArrayList<Stamp>();

        if(purposeState.equals("S")){
            stampBtn.setBackgroundColor(ContextCompat.getColor(StampActivity.this, R.color.colorSuccess));
            buttonText.setText("목표 성공");
        }else if(purposeState.equals("F")){
            stampBtn.setBackgroundColor(ContextCompat.getColor(StampActivity.this, R.color.colorFail));
            buttonText.setText("목표 실패");
        }

        // 스탬프 목록 및 목표 정보 받아오기
        if(getNetworkInfo() != null && getNetworkInfo().isConnected()){
            Call<List<Stamp>> call = stampCall.getStamp(purposeIdx);

            call.enqueue(new Callback<List<Stamp>>() {
                @Override
                public void onResponse(Call<List<Stamp>> call, Response<List<Stamp>> response) {
                    stamp = response.body();

                    if(stamp.get(0).getIdx() != 0){
                        StampOnViewAdapter stampOnViewAdapter = new StampOnViewAdapter(stamp);
                        recyclerView.setAdapter(stampOnViewAdapter);

                        purpose_name.setText(stamp.get(0).getPurpose().getPurposeName());
                        purpose_memo.setText(stamp.get(0).getPurpose().getPurposeMemo());
                        start_date.setText(stamp.get(0).getPurpose().getStartDate());
                        end_date.setText(stamp.get(0).getPurpose().getEndDate());

                        for(Stamp stamps : stamp){
                            if(stamps.getUpdateDate().equals("")){
                                offStampList.add(stamps);
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Stamp>> call, Throwable t) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());
                    ad.setMessage("서버 연결이 원활하지 않습니다.").show();
                }
            });
        } else{
            AlertDialog.Builder ad = new AlertDialog.Builder(StampActivity.this);
            ad.setMessage("네트워크 연결 상태를 확인해주세요.").show();
        }


        final FrameLayout stampBtn = findViewById(R.id.stampBtn);

        if(purposeState.equals("P")){
            stampBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                    dialog.setMessage("스탬프를 찍겠습니까?");
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ImageView view = recyclerView.getLayoutManager().findViewByPosition(offStampList.get(0).getPosition()).findViewById(R.id.stamp_view);
                            TextView stampYear = recyclerView.getLayoutManager().findViewByPosition(offStampList.get(0).getPosition()).findViewById(R.id.stamp_year);
                            TextView stampDate = recyclerView.getLayoutManager().findViewByPosition(offStampList.get(0).getPosition()).findViewById(R.id.stamp_date);
                            view.setBackground(getResources().getDrawable(R.drawable.stamp_on_circle));

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String dateStr = format.format(date);
                            stampYear.setText(dateStr.substring(0,4));
                            stampDate.setText(dateStr.substring(5));
                            stampYear.setVisibility(View.VISIBLE);
                            stampDate.setVisibility(View.VISIBLE);

                            Stamp stamp = new Stamp();
                            Purpose purpose = new Purpose();
                            purpose.setIdx(offStampList.get(0).getPurpose().getIdx());
                            stamp.setPosition(offStampList.get(0).getPosition());
                            stamp.setPurpose(purpose);

                            if(getNetworkInfo() != null && getNetworkInfo().isConnected()){
                                Call<List<Stamp>> call = stampCall.createStamp(stamp);
                                call.enqueue(new Callback<List<Stamp>>() {
                                    @Override
                                    public void onResponse(Call<List<Stamp>> call, Response<List<Stamp>> response) {
                                        List<Stamp> responseStampList = response.body();
//                                        purposeState = responseStamp.getPurpose().getPurposeState();
//                                        if(responseStamp.getPurpose().getPurposeState().equals("S")){
//                                            stampBtn.setEnabled(false);
//                                            stampBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSuccess));
//                                            buttonText.setText("목표 성공");
//                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Stamp>> call, Throwable t) {
                                        AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());
                                        ad.setMessage("서버 연결이 원활하지 않습니다.").show();
                                    }
                                });
                            } else{
                                AlertDialog.Builder ad = new AlertDialog.Builder(StampActivity.this);
                                ad.setMessage("네트워크 연결 상태를 확인해주세요.").show();
                            }

                            offStampList.remove(offStampList.get(0));
                        }
                    });

                    dialog.create().show();
                }
            });
        }
    }

    // 네트워크 정보
    private NetworkInfo getNetworkInfo(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(purposeState.equals("P")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.setting_menu, menu);
        } else{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.setting_menu_delete, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.setting){
            Intent intent = new Intent(this, PurposeModify.class);
            intent.putExtra("purposeIdx", purposeIdx);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.deleteBtn){
            AlertDialog.Builder builder = new AlertDialog.Builder(StampActivity.this);
            builder
                    .setMessage("목표를 삭제합니다.")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(getNetworkInfo() != null && getNetworkInfo().isConnected()){
                                Call<Purpose> call = purposeCall.deletePurpose(purposeIdx);
                                call.enqueue(new Callback<Purpose>() {
                                    @Override
                                    public void onResponse(Call<Purpose> call, Response<Purpose> response) {
                                        if(response.body().getPurposeName().equals("delete")){
                                            Toast.makeText(getApplicationContext(), "목표 삭제 완료", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else{
                                            AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());
                                            ad.setMessage("목표 삭제 실패!").show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Purpose> call, Throwable t) {
                                        AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());
                                        ad.setMessage("서버 연결이 원활하지 않습니다.").show();
                                    }
                                });
                            }
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}


class StampOnViewAdapter extends RecyclerView.Adapter<StampOnViewAdapter.ViewHolder> {

    private List<Stamp> mItems;
    private Context context;

    public StampOnViewAdapter(List<Stamp> items){
        mItems = items;
    }

    @Override
    public StampOnViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stamp_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        holder.setIsRecyclable(false);
        context = v.getContext();

        return holder;

    }

    @Override
    public void onBindViewHolder(StampOnViewAdapter.ViewHolder holder, final int position) {
        final StampCall stampCall = RetrofitNetwork.getInstance().getStampCall();

        if(!mItems.get(position).getUpdateDate().equals("")){
            final int idx = mItems.get(position).getIdx();

            final String updateDate = mItems.get(position).getUpdateDate();

            String year = updateDate.substring(0,4);
            holder.stampYear.setText(year);
            holder.stampYear.setVisibility(View.VISIBLE);

            String date = updateDate.substring(5);
            holder.stampDate.setText(date);
            holder.stampDate.setVisibility(View.VISIBLE);

            holder.stampCircle.setBackgroundResource(R.drawable.stamp_on_circle);

            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    final String str[] = {"수정"};
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder
                            .setItems(str, new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.N)
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    // 수정
                                    final Calendar date = Calendar.getInstance();

                                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                            // 선택 날짜
                                            Calendar newDate = Calendar.getInstance();
                                            newDate.set(year, monthOfYear, dayOfMonth);

                                            // 완료일
                                            Calendar calendar = Calendar.getInstance();

                                            if (newDate.getTime().after(calendar.getTime())) {
                                                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                                                ad.setMessage("오늘 날짜 이후로 날짜를 변경하실 수 없습니다.").show();
                                            } else {
                                                Stamp requestStamp = new Stamp();
                                                requestStamp.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd").format(newDate.getTime()));

                                                Call<Stamp> call = stampCall.updateDateStamp(idx, requestStamp);
                                                call.enqueue(new Callback<Stamp>() {
                                                    @Override
                                                    public void onResponse(Call<Stamp> call, Response<Stamp> response) {
                                                        if (response.body().getIdx() != 0) {
                                                            Toast.makeText(context, "스탬프 날짜 수정 완료", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(context, "스탬프 날짜 수정 실패", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Stamp> call, Throwable t) {
                                                        Toast.makeText(context, "서버 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                                    datePickerDialog.show();
                                }
                            }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View stampCircle;
        TextView stampYear;
        TextView stampDate;
        FrameLayout itemView;

        ViewHolder(final View view) {
            super(view);
            stampCircle = view.findViewById(R.id.stamp_view);
            stampYear = view.findViewById(R.id.stamp_year);
            stampDate = view.findViewById(R.id.stamp_date);
            itemView = view.findViewById(R.id.item);

        }
    }
}
