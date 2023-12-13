package com.example.chronus.AlarmNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static int mTaskId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Успех", "Успех");
        ToastUtil.showShort(context, "Успех");
        Intent i = new Intent(context, AlarmService.class);
        i.putExtra("alarm_time",
                DateTimeUtil.getNLaterDateTimeString(Calendar.MINUTE, 3));
        i.putExtra("task_id", mTaskId);
        context.startService(i);
    }
}