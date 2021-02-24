package com.surehappiness.app.service;

import android.os.Handler;

public class ServiceThread extends Thread {

    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    /**
     *  시간이 정확해야하는 애플리케이션에서는 로직 수행시간이 추가되면서 지속적인 오차 또는 시간 밀림 현상이 나타난다.
     *
     *   1초 != sleep(1000) + 로직 수행 시간
     */
    public void run(){
        //반복적으로 수행할 작업을 한다.
        try {
            Thread.sleep(10000);                // 알림 on 하면 10초 뒤 "/notification" call
        } catch (InterruptedException e) { }

        while(isRun){
            try{
                Thread.sleep(60000);            //1분 씩 쉰다.
                handler.sendEmptyMessage(0);    //쓰레드에 있는 핸들러에게 메세지를 보냄
            }catch (Exception e) {}
        }
    }
}
