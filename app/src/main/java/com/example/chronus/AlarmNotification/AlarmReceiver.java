package com.example.chronus.AlarmNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        ToastUtil.showNotifiction(context,title,"reminder");
    }

}