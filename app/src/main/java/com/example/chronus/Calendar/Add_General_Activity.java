package com.example.chronus.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chronus.R;

public class Add_General_Activity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener {
    private EditText title,content;
    private TextView close,sumbit,begin,end;
    private  Spinner spinner,spinner1;
    private int hour,hour1;
    private int item;
    private static final String tag="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_new_schedule);
        initView();
        initDate();
    }

    public void initView(){
        close=findViewById(R.id.tv_close);
        sumbit=findViewById(R.id.tv_submit);
        title=findViewById(R.id.AddTitle);
        content=findViewById(R.id.AddContent);
        begin=findViewById(R.id.tv_begin);
        end=findViewById(R.id.tv_end);
        spinner=findViewById(R.id.sp_end);
        spinner1=findViewById(R.id.sp_color);
        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        close.setOnClickListener(this);
        sumbit.setOnClickListener(this);
        end.setOnClickListener(this);

    }



    public   void initDate(){
        new Bundle();
        Bundle bundle = getIntent().getExtras();
        int year=bundle.getInt("year");
        int month=bundle.getInt("month");
        int day=bundle.getInt("day");
        hour=bundle.getInt("hour");
        String h=parseStr(hour);
        begin.setText(day+"."+month+"."+year+"."+" "+h+":00");
        spinner.setSelection(hour+1);
    }

    public  String parseStr(int a){

        if(a<10) return ("0"+a);
        else return (""+a);
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        if(view.getId()==R.id.tv_close){
            Log.d( tag,"close" );
            setResult(Activity.RESULT_CANCELED,intent);
            finish();
        }

        if(view.getId()==R.id.tv_submit){
            if(title.getText().toString().equals("")) {
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
            else{
                Bundle bundle=new Bundle();
                bundle.putString("title",title.getText().toString());
                bundle.putString("content",content.getText().toString());
                bundle.putInt("begin",hour);
                bundle.putInt("end",hour1);
                bundle.putInt("scheme",item);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.sp_end){
            if(i<=hour)
                spinner.setSelection(hour+1);
            else hour1=i;
        }
        if(adapterView.getId()==R.id.sp_color){
            item=i;
            setBgColor_radius( view,i );
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setBgColor_radius(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_radius);break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_radius);break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_radius);break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_radius);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_radius);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_radius);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_radius);break;
        }
    }
}
