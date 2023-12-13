package com.example.chronus.AlarmNotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

import static android.content.Context.ALARM_SERVICE;


public class Notify {

    private NotificationManager manager;
    private Notification.Builder builder;
    private Context context;
    private String TAG = "notify";

    public Notify(Context context){
        this.context = context;
    }
    private int i=1;
    @TargetApi(26)
    public void setNotification(String title, String desc){

        manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        String CHANNEL_ONE_ID = "com.zhangshiling.app";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);

            NotificationManager nm = context.getSystemService(NotificationManager.class);
            nm.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder = new Notification.Builder(context).setContentTitle(title)
                .setChannelId(CHANNEL_ONE_ID)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.GREEN, 1000, 2000)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.notify_ico);
        Log.i(TAG,"after build a builder");
        NotificationManagerCompat new_nm = NotificationManagerCompat.from(context);
        new_nm.notify(i++, builder.build());
    }

}

