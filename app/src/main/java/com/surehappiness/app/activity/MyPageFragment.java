package com.surehappiness.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.surehappiness.app.R;
import com.surehappiness.app.service.MyService;

import static android.content.Context.MODE_PRIVATE;

public class MyPageFragment extends Fragment {

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_mypage,null);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        final SharedPreferences userInfoPref = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        final SharedPreferences.Editor userInfoEditor = userInfoPref.edit();
        SharedPreferences notificationPref = getActivity().getSharedPreferences("notification", MODE_PRIVATE);
        final SharedPreferences.Editor notificationEditor = notificationPref.edit();

        TextView profile = view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserUpdateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        final Switch notificationSwitch = view.findViewById(R.id.notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                notificationEditor.putBoolean("switchOnOff", notificationSwitch.isChecked());
                notificationEditor.apply();

                if(isChecked){
                    Intent intent = new Intent(getActivity(), MyService.class);
                    getActivity().startService(intent);
                } else{
                    Intent intent = new Intent(getActivity(), MyService.class);
                    getActivity().stopService(intent);
                }
            }
        });
        boolean switchOnOff = notificationPref.getBoolean("switchOnOff", false);
        notificationSwitch.setChecked(switchOnOff);

        TextView logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationEditor.putBoolean("switchOnOff", false);
                notificationEditor.apply();

                Intent intent = new Intent(getActivity(), MyService.class);
                getActivity().stopService(intent);

                if(userInfoPref != null){
                    userInfoEditor.clear();
                    userInfoEditor.apply();
                }

                dialog.setMessage("로그아웃 되었습니다.");
                // 다이얼로그 외부 터치 시 다이얼로그가 종료되지 않도록
                dialog.setCancelable(false);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).create();
                dialog.show();
            }
        });

        return view;
    }
}