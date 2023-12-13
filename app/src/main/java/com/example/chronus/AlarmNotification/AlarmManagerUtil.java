package com.example.chronus.AlarmNotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmManagerUtil {

    public static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


    public static void sendAlarmBroadcast(Context context, int requestCode,
                                          int type,String title, long triggerAtTime, Class cls) {
        AlarmManager mgr = getAlarmManager(context);

        Intent intent = new Intent(context, cls);
        intent.putExtra("title",title);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode,
                intent, 0);

        mgr.set(type, triggerAtTime, pi);
    }

}
