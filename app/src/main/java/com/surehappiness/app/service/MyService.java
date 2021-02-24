package com.surehappiness.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.surehappiness.app.R;
import com.surehappiness.app.activity.LoginActivity;

public class MyService extends Service {

    NotificationManager notificationManager;
    ServiceThread thread;
    Notification notification ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업
    public void onDestroy() {
        thread.stopForever();
        thread = null;      //쓰레기 값을 만들어서 빠르게 회수하라고 null 을 넣어줌.
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if(NotificationService.instance.notificationOn(getApplicationContext())){
                Intent intent = new Intent(MyService.this, LoginActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

                notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("소확행 프로젝트")
                        .setContentText("목표를 달성하여 소소한 행복을 느끼세요!")
                        .setSmallIcon(R.drawable.ic_logo)
                        .setTicker("알림")
                        .setContentIntent(pendingIntent)
                        .build();

                //소리추가
                notification.defaults = Notification.DEFAULT_SOUND;
                //알림 소리를 한번만 내도록
                notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
                //확인하면 자동으로 알림이 제거 되도록
                notification.flags = Notification.FLAG_AUTO_CANCEL;

                //실행
                notificationManager.notify( 777 , notification);
            }
        }
    }
}