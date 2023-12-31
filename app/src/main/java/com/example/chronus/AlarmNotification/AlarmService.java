package com.example.chronus.AlarmNotification;

import java.util.Date;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class AlarmService extends IntentService {

    private String alarmDateTime;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        alarmDateTime = intent.getStringExtra("alarm_time");
        int taskId = intent.getIntExtra("task_id", 0);
        String title = intent.getStringExtra("title");

        Log.d("AlarmService", "executed at " + new Date().toString()
                + " @Thread id：" + Thread.currentThread().getId());

        long alarmDateTimeMillis = DateTimeUtil.stringToMillis(alarmDateTime);

        AlarmManagerUtil.sendAlarmBroadcast(this, taskId,
                AlarmManager.RTC_WAKEUP,title,alarmDateTimeMillis,
                AlarmReceiver.class);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "Alarm Service Destroy");
    }

}