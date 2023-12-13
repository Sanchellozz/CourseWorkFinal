package com.example.chronus.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalendarFragment extends Fragment implements CalendarView.OnDateSelectedListener, CalendarView.OnYearChangeListener{

    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private RelativeLayout mRelativeTool;
    private Activity activity;
    private CalendarLayout mCalendarLayout;
    private static MainActivity mainActivity;

    private Boolean ifFirstIn = true;
    private static int mYear,mMonth,mDay;

    private  static String date;

    private   List<Calendar> schemes= new ArrayList<>();

    private static int first,last;

    private int mHour;

    private static String title,content;




    public static int start,end,item;

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,
            btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24;


    private View line1,line2,line3,line4,line5,line6,line7,line8,line9,line10,line11,line12,line13,line14,line15,line16,
            line17,line18,line19,line20,line21,line22,line23;


    private View.OnClickListener listener;


    private View.OnFocusChangeListener listener1;

    private static final String ARG_SHOW_TEXT = "text";

    private View view;


    public static CalendarFragment newInstance(String param1){
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity=getActivity();
        mainActivity=new MainActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calendar,container,false);

        mTextMonthDay = view.findViewById(R.id.tv_month_day);
        mTextYear = view. findViewById(R.id.tv_year);
        mRelativeTool =  view.findViewById(R.id.rl_tool);
        mCalendarView = view. findViewById(R.id.calendarView);
        mTextCurrentDay = view.findViewById(R.id.tv_current_day);
        mCalendarLayout =view. findViewById(R.id.calendarLayout);


        btn1=view.findViewById(R.id.btn_1);
        btn2=view.findViewById(R.id.btn_2);
        btn3=view.findViewById(R.id.btn_3);
        btn4=view.findViewById(R.id.btn_4);
        btn5=view.findViewById(R.id.btn_5);
        btn6=view.findViewById(R.id.btn_6);
        btn7=view.findViewById(R.id.btn_7);
        btn8=view.findViewById(R.id.btn_8);
        btn9=view.findViewById(R.id.btn_9);
        btn10=view.findViewById(R.id.btn_10);
        btn11=view.findViewById(R.id.btn_11);
        btn12=view.findViewById(R.id.btn_12);
        btn13=view.findViewById(R.id.btn_13);
        btn14=view.findViewById(R.id.btn_14);
        btn15=view.findViewById(R.id.btn_15);
        btn16=view.findViewById(R.id.btn_16);
        btn17=view.findViewById(R.id.btn_17);
        btn18=view.findViewById(R.id.btn_18);
        btn19=view.findViewById(R.id.btn_19);
        btn20=view.findViewById(R.id.btn_20);
        btn21=view.findViewById(R.id.btn_21);
        btn22=view.findViewById(R.id.btn_22);
        btn23=view.findViewById(R.id.btn_23);
        btn24=view.findViewById(R.id.btn_24);


        line1=view.findViewById( R.id.ul_1 );
        line2=view.findViewById( R.id.ul_2 );
        line3=view.findViewById( R.id.ul_3);
        line4=view.findViewById( R.id.ul_4 );
        line5=view.findViewById( R.id.ul_5 );
        line6=view.findViewById( R.id.ul_6 );
        line7=view.findViewById( R.id.ul_7 );
        line8=view.findViewById( R.id.ul_8);
        line9=view.findViewById( R.id.ul_9 );
        line10=view.findViewById( R.id.ul_10 );
        line11=view.findViewById( R.id.ul_11);
        line12=view.findViewById( R.id.ul_12 );
        line13=view.findViewById( R.id.ul_13 );
        line14=view.findViewById( R.id.ul_14 );
        line15=view.findViewById( R.id.ul_15 );
        line16=view.findViewById( R.id.ul_16 );
        line17=view.findViewById( R.id.ul_17 );
        line18=view.findViewById( R.id.ul_18);
        line19=view.findViewById( R.id.ul_19 );
        line20=view.findViewById( R.id.ul_20 );
        line21=view.findViewById( R.id.ul_21 );
        line22=view.findViewById( R.id.ul_22 );
        line23=view.findViewById( R.id.ul_23 );


        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCalendarView.isYearSelectLayoutVisible()) {
                    mCalendarView.closeYearSelectLayout();
                    Toast.makeText( activity,"changeToMonthView",Toast.LENGTH_SHORT ).show();
                }
                mCalendarView.scrollToCurrent();
            }
        });




        listener1=new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    switch (v.getId()){
                        case R.id.btn_1:    btn1.setText("+createSchedule");mHour=0;return;
                        case R.id.btn_2:    btn2.setText("+createSchedule");mHour=1;return;
                        case R.id.btn_3:    btn3.setText("+createSchedule");mHour=2;return;
                        case R.id.btn_4:    btn4.setText("+createSchedule");mHour=3;return;
                        case R.id.btn_5:    btn5.setText("+createSchedule");mHour=4;return;
                        case R.id.btn_6:    btn6.setText("+createSchedule");mHour=5;return;
                        case R.id.btn_7:    btn7.setText("+createSchedule");mHour=6;return;
                        case R.id.btn_8:    btn8.setText("+createSchedule");mHour=7;return;
                        case R.id.btn_9:    btn9.setText("+createSchedule");mHour=8;return;
                        case R.id.btn_10:    btn10.setText("+createSchedule");mHour=9;return;
                        case R.id.btn_11:    btn11.setText("+createSchedule");mHour=10;return;
                        case R.id.btn_12:    btn12.setText("+createSchedule");mHour=11;return;
                        case R.id.btn_13:    btn13.setText("+createSchedule");mHour=12;return;
                        case R.id.btn_14:    btn14.setText("+createSchedule");mHour=13;return;
                        case R.id.btn_15:    btn15.setText("+createSchedule");mHour=14;return;
                        case R.id.btn_16:    btn16.setText("+createSchedule");mHour=15;return;
                        case R.id.btn_17:    btn17.setText("+createSchedule");mHour=16;return;
                        case R.id.btn_18:    btn18.setText("+createSchedule");mHour=17;return;
                        case R.id.btn_19:    btn19.setText("+createSchedule");mHour=18;return;
                        case R.id.btn_20:    btn20.setText("+createSchedule");mHour=19;return;
                        case R.id.btn_21:    btn21.setText("+createSchedule");mHour=20;return;
                        case R.id.btn_22:    btn22.setText("+createSchedule");mHour=21;return;
                        case R.id.btn_23:    btn23.setText("+createSchedule");mHour=22;return;
                        case R.id.btn_24:    btn24.setText("+createSchedule");mHour=23;return;
                    }
                }
                else {
                    switch (v.getId()){
                        case R.id.btn_1:    if(isAdd( btn1 )) btn1.setText(null);return;
                        case R.id.btn_2:    if(isAdd( btn2)) btn2.setText(null);return;
                        case R.id.btn_3:    if(isAdd( btn3 )) btn3.setText(null);return;
                        case R.id.btn_4:    if(isAdd( btn4 )) btn4.setText(null);return;
                        case R.id.btn_5:    if(isAdd( btn5 )) btn5.setText(null);return;
                        case R.id.btn_6:    if(isAdd( btn6 )) btn6.setText(null);return;
                        case R.id.btn_7:    if(isAdd( btn7 )) btn7.setText(null);return;
                        case R.id.btn_8:    if(isAdd( btn8 )) btn8.setText(null);return;
                        case R.id.btn_9:    if(isAdd( btn9 )) btn9.setText(null);return;
                        case R.id.btn_10:   if(isAdd( btn10 )) btn10.setText(null);return;
                        case R.id.btn_11:   if(isAdd( btn11 )) btn11.setText(null);return;
                        case R.id.btn_12:   if(isAdd( btn12 ))  btn12.setText(null);return;
                        case R.id.btn_13:   if(isAdd( btn13 )) btn13.setText(null);return;
                        case R.id.btn_14:   if(isAdd( btn14 ))  btn14.setText(null);return;
                        case R.id.btn_15:   if(isAdd( btn15 )) btn15.setText(null);return;
                        case R.id.btn_16:   if(isAdd( btn16 )) btn16.setText(null);return;
                        case R.id.btn_17:   if(isAdd( btn17 )) btn17.setText(null);return;
                        case R.id.btn_18:    if(isAdd( btn18 )) btn18.setText(null);return;
                        case R.id.btn_19:    if(isAdd( btn19 )) btn19.setText(null);return;
                        case R.id.btn_20:    if(isAdd( btn20 )) btn20.setText(null);return;
                        case R.id.btn_21:    if(isAdd( btn21 )) btn21.setText(null);return;
                        case R.id.btn_22:   if(isAdd( btn22 ))  btn22.setText(null);return;
                        case R.id.btn_23:    if(isAdd( btn23 )) btn23.setText(null);return;
                        case R.id.btn_24:    if(isAdd( btn24 )) btn24.setText(null);return;
                    }
                }
            }
        };


        btn1.setOnFocusChangeListener(listener1);
        btn2.setOnFocusChangeListener(listener1);
        btn3.setOnFocusChangeListener(listener1);
        btn4.setOnFocusChangeListener(listener1);
        btn5.setOnFocusChangeListener(listener1);
        btn6.setOnFocusChangeListener(listener1);
        btn7.setOnFocusChangeListener(listener1);
        btn8.setOnFocusChangeListener(listener1);
        btn9.setOnFocusChangeListener(listener1);
        btn10.setOnFocusChangeListener(listener1);
        btn11.setOnFocusChangeListener(listener1);
        btn12.setOnFocusChangeListener(listener1);
        btn13.setOnFocusChangeListener(listener1);
        btn14.setOnFocusChangeListener(listener1);
        btn15.setOnFocusChangeListener(listener1);
        btn16.setOnFocusChangeListener(listener1);
        btn17.setOnFocusChangeListener(listener1);
        btn18.setOnFocusChangeListener(listener1);
        btn19.setOnFocusChangeListener(listener1);
        btn20.setOnFocusChangeListener(listener1);
        btn21.setOnFocusChangeListener(listener1);
        btn22.setOnFocusChangeListener(listener1);
        btn23.setOnFocusChangeListener(listener1);
        btn24.setOnFocusChangeListener(listener1);



        listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn=(Button) v;

                if(isAdd( btn )){
                    Intent intent=new Intent(activity,Add_General_Activity.class);
                    Bundle bundle=new Bundle();

                    bundle.putInt("year",mYear);
                    bundle.putInt("month",mMonth);
                    bundle.putInt("day",mDay);
                    bundle.putInt("hour",mHour);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
                }

                else{

                    first=rangFirst( btn );
                    last=rangeLast( btn );
                    String id=mainActivity.get_Schedule_ID(date,""+first,""+last);
                    String Place=mainActivity.get_Place_In_Schedule(id);
                    String Content=mainActivity.get_Content_In_Schedule(id);
                    Intent intent=new Intent(activity,Delete_General_Activity.class);
                    Bundle bundle=new Bundle();

                    bundle.putInt("year1",mYear);
                    bundle.putInt("month1",mMonth);
                    bundle.putInt("day1",mDay);
                    bundle.putInt("begin",first);
                    bundle.putInt("end",last);
                    bundle.putString( "id",btn.getText().toString() );
                    bundle.putString("place",Place);
                    bundle.putString("content",Content);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }
            }
        };


        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btn10.setOnClickListener(listener);
        btn11.setOnClickListener(listener);
        btn12.setOnClickListener(listener);
        btn13.setOnClickListener(listener);
        btn14.setOnClickListener(listener);
        btn15.setOnClickListener(listener);
        btn16.setOnClickListener(listener);
        btn17.setOnClickListener(listener);
        btn18.setOnClickListener(listener);
        btn19.setOnClickListener(listener);
        btn20.setOnClickListener(listener);
        btn21.setOnClickListener(listener);
        btn22.setOnClickListener(listener);
        btn23.setOnClickListener(listener);
        btn24.setOnClickListener(listener);

        mCalendarView.setSchemeDate(schemes);
        initView();
        return view;
    }




    @SuppressLint("SetTextI18n")
    protected void initView() {

        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();

        mTextMonthDay.setText(mCalendarView.getCurMonth()  +" "+ mCalendarView.getCurDay() );

        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));


        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout( mYear );
                }
                mCalendarView.showYearSelectLayout( mYear );
                mTextYear.setVisibility( View.GONE );
                mTextMonthDay.setText( String.valueOf( mYear ) );
                if(!mCalendarView.isYearSelectLayoutVisible()){
                    Toast.makeText( activity, "changeToYearView", Toast.LENGTH_SHORT ).show();
                }
            }
        });

        List<String> list = new ArrayList<String>();


    }



    private Calendar getSchemeCalendar(int year, int month, int day, int color,String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme( text );

        return calendar;
    }


    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){

            if(resultCode==0){

                List<String> list = new ArrayList<String>();

                deleteView();
                refreshView(date,list);
                Toast.makeText( activity,"close",Toast.LENGTH_SHORT).show();
            }

            if(resultCode==-1){
                new Bundle();
                Bundle bundle1=data.getExtras();
                title=bundle1.getString("title");
                content=bundle1.getString("content");
                item=bundle1.getInt("scheme");
                start=bundle1.getInt("begin");
                end=bundle1.getInt("end");

                addGeneral(title,start,end,item);
                addMark();

                String id=getNewId();
                mainActivity.Insert_Schedule(id,date,""+start,""+end,title,content,""+item);
                Toast.makeText( activity,"created",Toast.LENGTH_SHORT).show();
            }}

        if(requestCode==1){

            if(resultCode==1){

                mainActivity.DELETE_Schedule(date,""+first,""+last);

                List<String> list = new ArrayList<String>();

                deleteView();
                refreshView(date,list);
                deleteMark();
                Toast.makeText( activity,"successfully delete",Toast.LENGTH_SHORT).show();
            }

            if(resultCode==-1){
                String Title,Place,Content;
                new Bundle();
                Bundle bundle2=data.getExtras();
                Title=bundle2.getString( "UpdateTitle" );
                Place=bundle2.getString( "UpdatePlace" );
                Content=bundle2.getString( "UpdateContent" );

                String id=mainActivity.get_Schedule_ID(date,""+first,""+last);
                mainActivity.Update_Schedule(Title,Place,Content,id);

                List<String> list = new ArrayList<String>();

                deleteView();
                refreshView(date,list);

            }

            if(resultCode==0){

                List<String> list = new ArrayList<String>();

                deleteView();
                refreshView(date,list);
            }
        }
    }

    private Button getBtn(int a) {
        switch (a) {
            case 0: return btn1;
            case 1: return btn2;
            case 2: return btn3;
            case 3: return btn4;
            case 4: return btn5;
            case 5: return btn6;
            case 6: return btn7;
            case 7: return btn8;
            case 8: return btn9;
            case 9: return btn10;
            case 10: return btn11;
            case 11: return btn12;
            case 12: return btn13;
            case 13: return btn14;
            case 14: return btn15;
            case 15: return btn16;
            case 16: return btn17;
            case 17: return btn18;
            case 18: return btn19;
            case 19: return btn20;
            case 20: return btn21;
            case 21: return btn22;
            case 22: return btn23;
            case 23: return btn24;
        }
        return null;
    }

    private View getView(int a) {
        switch (a) {
            case 0: return line1;
            case 1: return line2;
            case 2: return line3;
            case 3: return line4;
            case 4: return line5;
            case 5: return line6;
            case 6: return line7;
            case 7: return line8;
            case 8: return line9;
            case 9: return line10;
            case 10: return line11;
            case 11: return line12;
            case 12: return line13;
            case 13: return line14;
            case 14: return line15;
            case 15: return line16;
            case 16: return line17;
            case 17: return line18;
            case 18: return line19;
            case 19: return line20;
            case 20: return line21;
            case 21: return line22;
            case 22: return line23;

        }
        return null;
    }


    public boolean isAdd(Button btn){
        return btn.getText().toString().equals( "+createSchedule" );
    }

    public int rangFirst(Button btn){
        switch (btn.getId()){
            case R.id.btn_1:    return 0;
            case R.id.btn_2:   return 1;
            case R.id.btn_3:    return 2;
            case R.id.btn_4:    return 3;
            case R.id.btn_5:    return 4;
            case R.id.btn_6:    return 5;
            case R.id.btn_7:    return 6;
            case R.id.btn_8:    return 7;
            case R.id.btn_9:    return 8;
            case R.id.btn_10:   return 9;
            case R.id.btn_11:   return 10;
            case R.id.btn_12:   return 11;
            case R.id.btn_13:   return 12;
            case R.id.btn_14:   return 13;
            case R.id.btn_15:   return 14;
            case R.id.btn_16:   return 15;
            case R.id.btn_17:   return 16;
            case R.id.btn_18:    return 17;
            case R.id.btn_19:    return 18;
            case R.id.btn_20:    return 19;
            case R.id.btn_21:    return 20;
            case R.id.btn_22:   return 21;
            case R.id.btn_23:    return 22;
            case R.id.btn_24:    return 23;
        }
        return 0;
    }


    public int rangeLast(Button btn){
        int first=rangFirst( btn );
        int last=first+1;
        while(!getBtn( last ).isEnabled()){
            last++;
        }

        return last;
    }


    private void  addAllMark(){
        int year,month,day;
        String[] strs=new  String[3];
        List<String>list=new ArrayList<String>();
        list=mainActivity. Find_All_Date();
        Iterator iterator=list.iterator();
        if(list.isEmpty()){
            return;
        }
        while (iterator.hasNext()){
            strs=iterator.next().toString().split("-");
            year=Integer.parseInt(strs[0]);
            month=Integer.parseInt(strs[1]);
            day=Integer.parseInt(strs[2]);
            schemes.add(getSchemeCalendar(year ,month ,day , 0xFFFF7F00, " "));
        }
        mCalendarView.setSchemeDate(schemes);
    }

    private void addMark() {
        Calendar[] a=new Calendar[50];
        for(int j=0;j<schemes.size();j++){
            a[j]=schemes.get(j);
        }

        if(schemes.isEmpty()){
            schemes.add(getSchemeCalendar(mYear, mMonth, mDay, 0xFFFF7F00," "));
            mCalendarView.setSchemeDate(schemes);
            return;
        }

        for(int i=0;i<schemes.size();i++){
            boolean x=(a[i].getDay()==mDay)&&(a[i].getMonth()==mMonth)&&(a[i].getYear()==mYear);
            if((a[i].getDay()==mDay)&&(a[i].getMonth()==mMonth)&&(a[i].getYear()==mYear))
            {return;}
        }
        schemes.add(getSchemeCalendar(mYear, mMonth, mDay, 0xFFFF7F00," "));
        mCalendarView.setSchemeDate(schemes);
    }


    protected void deleteMark() {
        List<String>list=new ArrayList<String>();
        list=mainActivity.find_ID_By_date(date);
        if(list.size()==0){
            schemes.remove(getSchemeCalendar(mYear, mMonth, mDay, 0xFFFF7F00," "));
            mCalendarView.setSchemeDate(schemes);
        }

    }


    private  void setUnderLine(int begin,int finish){
        Button bt1,bt2;
        bt1=getBtn( begin -1);
        bt2=getBtn( finish);

        if(!bt1.isEnabled()){
            getView( begin-1 ).setBackgroundResource( R.drawable.bg_underline1 );
        }
        else if((!bt1.getText().toString().equals( null ))&&(!isAdd( bt1 ))) {
            getView( begin-1 ).setBackgroundResource( R.drawable.bg_underline1 );
        }

        if((!bt2.getText().toString().equals( null ))&&(!isAdd( bt2 ))){
            getView( finish-1 ).setBackgroundResource( R.drawable.bg_underline1 );
        }
    }




    public void setBgColor_head(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_head);break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_head);break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_head);break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_head);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_head);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_head);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_head);break;
        }
    }

    public void setBgColor_rang(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_rang );break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_rang );break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_rang );break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_rang);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_rang);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_rang);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_rang );break;
        }
    }

    public void setBgColor_tail(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_tail);break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_tail);break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_tail);break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_tail);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_tail);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_tail);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_tail);break;
        }
    }


    public void addGeneral(String title,int start,int end,int item){

        getBtn(start).setText( title );
        setBgColor_head( getBtn( start ),item );
        setBgColor_tail( getBtn( end-1 ),item );
        if(start==end-1){

        }
       else{
            getBtn( end-1 ).setEnabled( false );
        }

        for(int i=start+1;i<end-1;i++){
            setBgColor_rang( getBtn( i ),item );
            getBtn( i ).setEnabled( false );
        }

        for(int i=start;i<end-1;i++)
        {
            setBgColor_rang( getView( i ),item );
        }

        getBtn( start ).setFocusable( false );


        setUnderLine(start,end);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {

        mTextYear.setVisibility(View.VISIBLE);
        String EnglishMonth=null;
        switch (calendar.getMonth()){
            case 1: EnglishMonth="January";
                    break;
            case 2: EnglishMonth="February";
                    break;
            case 3: EnglishMonth="March";
                    break;
            case 4: EnglishMonth="April";
                    break;
            case 5: EnglishMonth="May";
                    break;
            case 6: EnglishMonth="June";
                    break;
            case 7: EnglishMonth="July";
                    break;
            case 8: EnglishMonth="August";
                    break;
            case 9: EnglishMonth="September";
                    break;
            case 10: EnglishMonth="October";
                    break;
            case 11: EnglishMonth="November";
                    break;
            case 12: EnglishMonth="December";
                    break;

        }
        mTextMonthDay.setText(EnglishMonth + " " + calendar.getDay() );
        mTextYear.setText(String.valueOf(calendar.getYear()));

        mYear = calendar.getYear();
        mMonth=calendar.getMonth();
        mDay=calendar.getDay();


        date = mYear+"-"+mMonth+"-"+mDay;

        if(ifFirstIn){
            addAllMark();
            ifFirstIn=false;
        }

        List<String> list = new ArrayList<String>();


        deleteView();
        refreshView(date,list);
        if(isClick){

        }
    }


    public  void deleteView(){

        for(int i=0;i<24;i++){
            getBtn( i ).setEnabled(false );
            getBtn( i ).setEnabled( true );
            getBtn( i ).setFocusableInTouchMode( true );
            getBtn(i).clearFocus();
            getBtn(i).setText(null);
            getBtn( i ).setBackgroundResource( R.drawable.bg_btn );
        }

        for(int i=0;i<23;i++){
            getView( i ).setBackgroundResource( R.drawable.bg_underline );
        }
    }

    public void refreshView(String date,List<String> l){
        String id,title;
        int start,end,color;
        l=mainActivity.find_ID_By_date(date);
        Iterator iterator=l.iterator();

        while (iterator.hasNext()){
            id=iterator.next().toString();
            title=mainActivity.get_Title_In_Schedule(id);
            Log.d("title",title);
            start=mainActivity.get_StartTime_In_Schedule(id);
            end=mainActivity.get_EndTime_In_Schedule(id);
            color=mainActivity.get_Color_In_Schedule(id);
            addGeneral(title,start,end,color);
        }


    }

    public String getNewId(){
        List<String>list=new ArrayList<String>();
        list=mainActivity.find_ID_By_date(date);
        int[] a=new int[24];
        a=getOldID();
        if(list.isEmpty()) return date+"-"+1;
        else{
            for(int i=1;i<=24;i++){
                if(!isOldID(a,i)) return date+"-"+i;
            }
        }
        return null;
    }


    private int[] getOldID(){
        List<String>list=new ArrayList<String>();
        list= mainActivity.find_ID_By_date(date);
        int[] a=new int[24];
        int i=0;
        Iterator iterator=list.iterator();
        while (iterator.hasNext()){
            a[i]=Integer.parseInt(getX(iterator.next().toString()));
            i++;
        }
        return a;
    }

    public String getX(String id){
        String[] strings=new String[4];
        strings=id.split("-");
        return strings[3];
    }


    private  boolean isOldID(int[] a,int i){
        for(int j=0;j<a.length;j++){
            if(a[j]==i) return true;
        }
        return false;
    }
}
