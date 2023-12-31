package com.example.chronus.Reminders;

import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.chronus.AlarmNotification.AlarmManagerUtil;
import com.example.chronus.AlarmNotification.AlarmReceiver;
import com.example.chronus.AlarmNotification.AlarmService;
import com.example.chronus.AlarmNotification.DateTimeUtil;
import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.util.Calendar;
import java.util.Date;


public class Update_DATA_Activity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener{
    private Integer year, month, day, hour, minute;

    private StringBuffer date, time;

    private Context context;
    private RelativeLayout llDate, llTime;
    private TextView tvDate, tvTime;

    EditText item_title;
    EditText item_content;
    TextView item_type;
    TextView cancel_tv;
    String item_id,item_from_listname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.reminder_edit_item);


        Intent intent = getIntent();
        item_id = intent.getStringExtra("item_id");
        item_from_listname = intent.getStringExtra("item_from_listname");


        item_title = (EditText)findViewById(R.id.title_et);
        item_content = findViewById(R.id.content_et);
        item_type = findViewById(R.id.item_type);
        cancel_tv = findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
        llDate =  findViewById(R.id.ll_date);
        tvDate =  findViewById(R.id.tv_date);
        llTime =  findViewById(R.id.ll_time);
        tvTime =  findViewById(R.id.tv_time);
        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
        tvDate.setText("AddDate");
        tvDate.setTextColor(Color.parseColor("#A4A2A4"));
        tvTime.setText("AddTime");
        tvTime.setTextColor(Color.parseColor("#A4A2A4"));

        item_title.setText(MainActivity.Get_Title_by_ID(item_id));
        item_content.setText(MainActivity.Get_Content_by_ID(item_id));
        item_type.setText(item_from_listname);


        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initDateTime();
    }

    public  void EDIT( View view)
    {
        MediaPlayer mMediaPlayer;
        mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.confirm_up);
        mMediaPlayer.start();




        String title =  item_title.getText().toString();
        String item_des = item_content.getText().toString();
        if(item_des == null){
            item_des=" ";
        }
        Integer Infactmonth = month+1;

        String Alerttime = null;

        Boolean Notife_or_not = false;


        if(tvDate.getText().equals("AddDate")){
            Alerttime = "Время не установлено";
            Notife_or_not =false;
        }else if(tvTime.getText().equals("AddTime")){

            Alerttime = year.toString()+"-"+(Infactmonth).toString()+"-"+
                    day.toString()+"-"+"9"+"-"+"0";
            Notife_or_not = true;
        }else if(tvTime.getText() != null){
            Alerttime = year.toString()+"-"+(Infactmonth).toString()+"-"+
                    day.toString()+"-"+hour.toString()+"-"+minute.toString();
            Notife_or_not = true;
        }

        if(!title.equals("")){

            MainActivity.Update_Remind_by_ID(item_type.getText().toString(),item_id,title,item_des);
            this.finish();
        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("напоминание");
            builder.setMessage("Установите название");
            builder.create();
            builder.show();
        }
        if(Notife_or_not){
            Calendar settingTime = Calendar.getInstance();
            settingTime.set(Calendar.YEAR,year);
            settingTime.set(Calendar.MONTH,month);
            settingTime.set(Calendar.DAY_OF_MONTH,day);
            settingTime.set(Calendar.HOUR_OF_DAY,hour);
            settingTime.set(Calendar.MINUTE,minute);
            settingTime.set(Calendar.SECOND,0);
            settingTime.set(Calendar.MILLISECOND, 0);
            int day = settingTime.get(Calendar.DAY_OF_YEAR);
            int day_now = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            int hour = settingTime.get(Calendar.HOUR_OF_DAY);
            int hour_now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int min = settingTime.get(Calendar.MINUTE);
            int min_now = Calendar.getInstance().get(Calendar.MINUTE);
            int sec = settingTime.get(Calendar.SECOND);
            int sec_now = Calendar.getInstance().get(Calendar.SECOND);
            int count_time;
            if(day>day_now){
                count_time = sec - sec_now + (min - min_now)*60 + (24 - hour_now + hour + 24 * (day-day_now-1))*3600 ;
            }else{
                count_time = sec - sec_now + (min - min_now)*60 + (hour - hour_now)*3600 ;
            }


            Intent i = new Intent(getBaseContext(), AlarmService.class);
            i.putExtra("alarm_time", DateTimeUtil.getNLaterDateTimeString(Calendar.SECOND,count_time));
            i.putExtra("task_id", item_id);
            i.putExtra("title",title);
            startService(i);
        }





        this.finish();
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) {
                            date.delete(0, date.length());
                        }
                        tvDate.setText(date.append(String.valueOf(year)).append(".").append(String.valueOf(month)).append(".").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("Установить дату");
                dialog.setView(dialogView);
                dialog.show();
                datePicker.init(year, month-1 , day, this);
                break;
            case R.id.ll_time:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) {
                            time.delete(0, time.length());
                        }
                        tvTime.setText(time.append(String.valueOf(hour)).append(".").append(String.valueOf(minute)).append("."));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setIs24HourView(true);
                timePicker.setOnTimeChangedListener(this);
                dialog2.setTitle("Установите время");
                dialog2.setView(dialogView2);
                dialog2.show();
                break;
        }
    }
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

}
