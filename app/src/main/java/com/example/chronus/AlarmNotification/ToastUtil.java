package com.example.chronus.AlarmNotification;



import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void showShort(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }


    public static void showNotifiction(Context context ,String info,String type_from){
        Notify nm = new Notify(context);
        if(type_from.equals("reminder")){
            nm.setNotification("Напоминание", info);
        }else if(type_from.equals("calendar")){
            nm.setNotification("календарь", info);
        }else {
            nm.setNotification("Уведомление", info);
        }
    }
}