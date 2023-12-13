package com.example.chronus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.chronus.Reminders.RemindersFragment;
import com.example.chronus.TimeLine.TimeLineFragment;
import com.example.chronus.Calendar.CalendarFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener , RemindersFragment.OnTitleSelectedListener{

    public ViewPager mViewPager;

    private List<Fragment>  mFragments;
    private FragmentPagerAdapter mAdapter;

    public static SQDB mDBHelper;

    public static int Line;

    public static String type;

    public  static String Edit_ID;
    private static Context context;

    public static String user_name = "admin";

    public static String type2;
    public static String title;
    public static String content;


    private ImageView iv_cal;
    private ImageView iv_rem;
    private ImageView iv_timeline;
    private ImageView iv_tom;
    private ImageView iv_set;
    static private Integer[] imgIds = new Integer[]{R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3,
            R.mipmap.lise_icon4, R.mipmap.lise_icon5, R.mipmap.lise_icon6};


    public static String host = "212.64.17.232";
    public static int port = 31247;
    public static MainActivity mainActivity;


    private int chooseTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);



        setContentView(R.layout.activity_main);




        mDBHelper = new SQDB(this);
        String localFileName = "test_demo.db";
        String remoteFileName = "test_demo.db";
        mainActivity =this;
        user_name = "admin";
        initView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

    }

    private void initView(){
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        mViewPager = findViewById(R.id.fragment_vp);

        mFragments = new ArrayList<>();
        mFragments.add(CalendarFragment.newInstance("Календарь"));
        mFragments.add(RemindersFragment.newInstance("To-do List"));
        mFragments.add(TimeLineFragment.newInstance("Лента"));
        //mFragments.add(TomatoFragment.newInstance("Таймер"));
       // mFragments.add(SettingFragment.newInstance("Настройки"));
        iv_cal = findViewById(R.id.calendar_tab);
        iv_rem = findViewById(R.id.remainder_tab);
        iv_timeline = findViewById(R.id.timeline_tab);
        iv_tom = findViewById(R.id.tomato_tab);
        iv_set = findViewById(R.id.settings_tab);

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        iv_rem.setImageResource(R.drawable.reminderbar_fill);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);

        final SharedPreferences SP_user = getSharedPreferences("user_name",MODE_PRIVATE);
        String user = SP_user.getString("user_name","admin");
        if(!user.equals("admin")){
            MainActivity.user_name = user;
            Log.d("user",MainActivity.user_name);
            Toast.makeText(this, "Welcome Back！",Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.calendar_tab).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                chooseTab=1;
                mViewPager.setCurrentItem(0,false);
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar_fill);
                iv_rem.setImageResource(R.drawable.reminderbar);
                iv_tom.setImageResource(R.drawable.clock);
                iv_cal.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_cal.setPadding(iv_rem.getPaddingLeft(),iv_rem.getPaddingTop(),iv_rem.getPaddingRight(),iv_rem.getPaddingBottom());
                        }

                }, 200);



            }
        });

        findViewById(R.id.timeline_tab).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mViewPager.setCurrentItem(2,false);
                chooseTab=3;
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar);
                iv_tom.setImageResource(R.drawable.clock);
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
            }
        });

        findViewById(R.id.remainder_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTab=2;
                mViewPager.setCurrentItem(1,false);
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar_fill);
                iv_tom.setImageResource(R.drawable.clock);
                iv_rem.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_rem.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                    }

                }, 200);

            }
        });
        findViewById(R.id.tomato_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTab=4;
                mViewPager.setCurrentItem(3,false);
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar);
                iv_tom.setImageResource(R.drawable.clock_fill);
                iv_tom.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_tom.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                    }

                }, 200);


            }
        });
        findViewById(R.id.settings_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTab=5;
                mViewPager.setCurrentItem(4,false);
                iv_set.setImageResource(R.drawable.set_fill);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar);
                iv_tom.setImageResource(R.drawable.clock);
                iv_set.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_set.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                    }

                }, 200);


            }
        });
        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        Boolean isFirstIn = sharedPreferences.getBoolean("is_first_in_data",true);

        if(isFirstIn){
            initDateofFirstLogin();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_first_in_data",false);
            editor.apply();
        }else{
            Log.d("Это первый вход?","нет");
        }

    }



    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            MediaPlayer mMediaPlayer;
            switch (position){
                case 0 :
                    chooseTab=1;
                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar_fill);
                    iv_rem.setImageResource(R.drawable.reminderbar);

                    iv_tom.setImageResource(R.drawable.clock);
                    iv_cal.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_cal.setPadding(iv_rem.getPaddingLeft(),iv_rem.getPaddingTop(),iv_rem.getPaddingRight(),iv_rem.getPaddingBottom());
                        }

                    }, 200);
                    break;
                case 1 :
                    chooseTab=2;

                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar_fill);
                    iv_tom.setImageResource(R.drawable.clock);
                    iv_rem.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_rem.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                        }

                    }, 200);
                    break;
                case 2 :
                    chooseTab=3;
                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    iv_tom.setImageResource(R.drawable.clock);

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    break;
                case 3 :
                    chooseTab=4;

                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    //iv_timeline.setImageResource(R.drawable.setting);
                    iv_tom.setImageResource(R.drawable.clock_fill);
                    iv_tom.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_tom.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                        }

                    }, 200);
                    break;
                case 4 :
                    chooseTab=5;

                    iv_set.setImageResource(R.drawable.set_fill);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    iv_tom.setImageResource(R.drawable.clock);
                    iv_set.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_set.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                        }

                    }, 200);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class VerticalViewPager extends ViewPager {

        public VerticalViewPager(Context context) {
            super(context);
            init();
        }

        public VerticalViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            setOverScrollMode(OVER_SCROLL_NEVER);
        }

        private class VerticalPageTransformer implements ViewPager.PageTransformer {

            @Override
            public void transformPage(View view, float position) {

                if (position < -1) { // [-Infinity,-1)
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    view.setAlpha(1);

                    view.setTranslationX(view.getWidth() * -position);

                    float yPosition = position * view.getHeight();
                    view.setTranslationY(yPosition);

                } else {
                    view.setAlpha(0);
                }
            }
        }


        private MotionEvent swapXY(MotionEvent ev) {
            float width = getWidth();
            float height = getHeight();

            float newX = (ev.getY() / height) * width;
            float newY = (ev.getX() / width) * height;

            ev.setLocation(newX, newY);

            return ev;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev);
            return intercepted;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {

            return super.onTouchEvent(swapXY(ev));
        }
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }



    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.calendar_tab:
                chooseTab=1;
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.remainder_tab:
                chooseTab=2;
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.timeline_tab:
                chooseTab=3;
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.tomato_tab:
                chooseTab=4;
                mViewPager.setCurrentItem(3,false);
                break;
            case R.id.settings_tab:
                chooseTab=5;
                mViewPager.setCurrentItem(4,false);
                break;
        }
    }

    @Override
    public void onEventsSelected(int position){




    }


    private static SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");
    private static Date date = new Date();
    private static String Day = formatt.format(date);


    public static void INSERT(String type, String id, String title,String m,String time) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        db.execSQL("INSERT INTO Remind_List(Type,ID,TITLE,DAY,Content,Checked,User_name) values(?,?,?,?,?,?,?)",
                new String[]{type, id,title,time,m,"0",user_name});

    }

    public static void DELETE(String id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Remind_List WHERE ID = ?", new String[]{id});
    }

    public  static String ShowLineID(int i){

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("Remind_List",null,null,null,null,null,null,null);

        if (cursor.getCount()<=0){
            result.append("Нет информации");
            cursor.close();
            return result.toString();
        }
        else if(i<cursor.getCount())
        {
            cursor.moveToPosition(i);
            String id = cursor.getString(cursor.getColumnIndex("ID"));

            cursor.close();
            return id;
        }
        else{
            result.append("Нет информации");
            cursor.close();
            return result.toString();
        }

    }

    public  static String ShowLineTitle_In_Type(int i,String type,String T) {
        StringBuilder title = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE Type =? AND Checked=? AND User_name =?", new String[]{type,T,user_name});


        if (cursor.getCount() <= 0) {
            title.append("Нет информации");
            cursor.close();
            return title.toString();
        } else {
            cursor.moveToPosition(i);
            title.append(cursor.getString(cursor.getColumnIndex("TITLE")));

            cursor.close();
            return title.toString();
        }
    }



    public static int getCount( )
    {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM Remind_List",null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    public static int getCount_By_Type(String type,String T)
    {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM Remind_List WHERE Type=? AND Checked=? AND User_name =?",new String[]{type,T,user_name});
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    public static String FIND(String id) {

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE ID = ? AND User_name =?",
                new String[]{id,user_name});

        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex("Content"));


            cursor.close();
            return  result.append(content).toString();
        } else {
            cursor.close();
            return result.append("Неизвестная ошибка").toString();
        }
    }



    public static String ShowDate(String id) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE ID = ? AND User_name =?", new String[]{id,user_name});

        if (cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex("DAY"));

            cursor.close();
            return result.append(data).toString();
        } else {
            cursor.close();
            return result.append("Неизвестная ошибка").toString();
        }
    }


    public static void SetFinished_By_Title(String title)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET Checked =? WHERE TITLE = ?AND User_name =?",
                new String[]{"1",title,user_name});


    }


    public static void Set_UnFinished_By_Title(String title)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET Checked =? WHERE TITLE = ?AND User_name = ?",
                new String[]{"0",title,user_name});


    }



    public static int get_ListCount( ){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM List WHERE User_name =?",new String[]{user_name});
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    public  static String ShowLineID_inList(int i){

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("List",new String[]{"User_name","ID"},"User_name = ?",new String[]{user_name},null,null,null);

        if (cursor.getCount()<=0){
            result.append("Нет информации");
            cursor.close();
            return result.toString();
        }
        else if(i<cursor.getCount())
        {
            cursor.moveToPosition(i);
            String id = cursor.getString(cursor.getColumnIndex("ID"));

            cursor.close();
            return id;
        }
        else{
            result.append("Нет информации");
            cursor.close();
            return result.toString();
        }
    }

    public static String Show_List_name(String id) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM List WHERE ID = ?AND User_name =?",
                new String[]{id,user_name});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));

            cursor.close();
            return result.append(name).toString();
        } else {
            cursor.close();
            return result.append("Неизвестная ошибка").toString();
        }
    }

    public static String Show_List_number(String id) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM List WHERE ID = ?AND User_name =?",
                new String[]{id,user_name});

        if (cursor.moveToFirst()) {
            String number = cursor.getString(cursor.getColumnIndex("number"));

            cursor.close();
            return result.append(number).toString();
        } else {
            cursor.close();
            return result.append("Неизвестная ошибка").toString();
        }
    }
    public static void INSERT_List(String id, String name,String icon_color, String number) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        db.execSQL("INSERT INTO List(ID,name,icon_color,number,User_name) values(?,?,?,?,?)",
                new String[]{id, name,icon_color,number,user_name});
    }
    public static void update_List(String name, String  id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE List SET name =?  WHERE ID = ?AND User_name =?",
                new String[]{name,id,user_name});
    }
    public static void Increase_List_Number(String type)
    {
        String number_before;
        SQLiteDatabase db1 = mDBHelper.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM List WHERE name = ?AND User_name =?",
                new String[]{type,user_name});
        if (cursor.moveToFirst()) {
            number_before = cursor.getString(cursor.getColumnIndex("number"));

            cursor.close();

        } else {
            number_before = "0";
            cursor.close();

        }
        db1.close();
        Integer i = Integer.parseInt(number_before);
        i++;
        SQLiteDatabase db2 = mDBHelper.getWritableDatabase();
        db2.execSQL("UPDATE List SET number =?  WHERE name = ?AND User_name =?",
                new String[]{i.toString(),type,user_name});
    }
    public static int Show_List_Color_By_ID(String id)
    {
        String color;
        SQLiteDatabase db =mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM List WHERE ID =?AND User_name =?", new String[]{id,user_name});
        if (cursor.moveToFirst()) {
            color = cursor.getString(cursor.getColumnIndex("icon_color"));

            cursor.close();
            return  Integer.parseInt(color);
        } else {
            cursor.close();
            return 0;
        }
    }

    public static void DELETE_LIST_By_ID(String id) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Remind_List WHERE Type = ?AND User_name =?", new String[]{Show_List_name(id),user_name});

        db.execSQL("DELETE FROM List WHERE ID = ?AND User_name =?", new String[]{id,user_name});

    }



    public static List<String> find_ID_By_date(String date) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String pattern = ".*" + date + ".*";
        String day;
        List<String> a =new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM Schedule WHERE User_name =? ", new String[]{MainActivity.user_name});

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                day = cursor.getString(cursor.getColumnIndex("Date"));

                if (Pattern.matches(pattern, day)) {
                    a.add(cursor.getString(cursor.getColumnIndex("ID")));
                }

            }
            cursor.close();
            return a;
        } else {
            cursor.close();
            return a;
        }

    }


    public static List<String> Find_All_Date()
    {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * FROM Schedule WHERE User_name =?",new String[]{user_name});

        List<String> a =new ArrayList<String>();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String temp= cursor.getString(cursor.getColumnIndexOrThrow("Date"));
                if(a.contains(temp))continue;
                a.add(temp);

            }
            cursor.close();
            return a;
        } else {
            cursor.close();
            return a;
        }
    }


    public static void Insert_Schedule(String id, String date, String start_time, String end_time, String title, String content,String bg_color){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("INSERT INTO Schedule(ID ,Date,Start_Time,End_Time,Title," +
                        "Content,bg_Color,User_name) values(?,?,?,?,?,?,?,?)",
                new String[]{id,date,start_time,end_time,title,content,bg_color,user_name});

    }

    public static String get_Title_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});


        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("Title"));

            cursor.close();
            return title;
        } else {
            cursor.close();
            return "Неизвестная контент ошибка";
        }
    }

    public static int get_StartTime_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});


        if (cursor.moveToFirst()) {
            String start_time = cursor.getString(cursor.getColumnIndex("Start_Time"));
            Integer s = Integer.parseInt(start_time);
            cursor.close();
            return s;
        } else {
            cursor.close();
            return 0;
        }
    }


    public static int get_EndTime_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});


        if (cursor.moveToFirst()) {
            String end_time = cursor.getString(cursor.getColumnIndex("End_Time"));
            Integer s = Integer.parseInt(end_time);
            cursor.close();
            return s;
        } else {
            cursor.close();
            return 0;
        }
    }



    public static String get_Place_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});


        if (cursor.moveToFirst()) {
            String place = cursor.getString(cursor.getColumnIndex("Place"));

            cursor.close();
            return place;
        } else {
            cursor.close();
            return "Неизвестная контент ошибка";
        }
    }



    public static String get_Content_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});


        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex("Content"));

            cursor.close();
            return content;
        } else {
            cursor.close();
            return "Неизвестная контент ошибка";
        }
    }


    public static int get_Color_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});


        if (cursor.moveToFirst()) {
            String bg_color = cursor.getString(cursor.getColumnIndex("bg_Color"));
            Integer s = Integer.parseInt(bg_color);
            cursor.close();
            return s;
        } else {
            cursor.close();
            return 0;
        }
    }


    public static void DELETE_Schedule(String date_time, String start_time,String end_time){

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Schedule WHERE Date = ?AND Start_Time =? AND End_Time =?AND User_name =?", new String[]{date_time,start_time,end_time,MainActivity.user_name});

    }

    public static void Update_Schedule(String title,String content,String place,String id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Schedule SET Title =? ,Content =?,Place =? WHERE ID = ? AND User_name =?",
                new String[]{title,content,place,id,user_name});
    }


    public static String get_Schedule_ID(String date,String start_time,String end_time){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND Date =?AND Start_Time =? AND End_Time =? ", new String[]{user_name, date,start_time,end_time});

        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("ID"));

            cursor.close();
            return id;
        } else {
            cursor.close();
            return "Неизвестная контент ошибка";
        }

    }

    public static String ShowLineID_inType(int i,String type,String T){

        StringBuilder id = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE Type =? AND Checked=? AND User_name =?", new String[]{type,T,user_name});
        if (cursor.getCount() <= 0) {
            id.append("Нет инфо");
            cursor.close();
            return id.toString();
        } else {
            cursor.moveToPosition(i);
            id.append(cursor.getString(cursor.getColumnIndex("ID")));

            cursor.close();
            return id.toString();
        }
    }

    public static String Get_Day_by_ID(String id ){
        StringBuilder day = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE ID =?  AND User_name =?", new String[]{id,user_name});
        if (cursor.moveToFirst()) {
            day.append( cursor.getString(cursor.getColumnIndex("DAY")));
            cursor.close();
            return day.toString();
        } else {
            cursor.close();
            return day.append("Неизвестная ошибка").toString();
        }
    }

    public static String Get_Title_by_ID(String id ){
        StringBuilder content = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE ID =?  AND User_name =?", new String[]{id,user_name});

        if (cursor.moveToFirst()) {
            content.append( cursor.getString(cursor.getColumnIndex("TITLE")));
            cursor.close();
            return content.toString();
        } else {
            cursor.close();
            return content.append("Неизвестная ошибка").toString();
        }
    }

    public static String Get_Content_by_ID(String id ){
        StringBuilder content = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE ID =?  AND User_name =?", new String[]{id,user_name});

        if (cursor.moveToFirst()) {
            content.append( cursor.getString(cursor.getColumnIndex("Content")));
            cursor.close();
            return content.toString();
        } else {
            cursor.close();
            return content.append("Неизвестная ошибка").toString();
        }
    }


    public static void Update_Remind_by_ID(String type, String  id,String title, String content) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET TITLE = ?, Content = ? WHERE Type =? AND ID = ? AND User_name =?",
                new String[]{title,content,type,id,user_name});

    }


    public static List<String> find_ID_By_DAY(String day)
    {
        List<String> a= new ArrayList<String>();
        SQLiteDatabase db =mDBHelper.getReadableDatabase();
        String pattern = ".*" + day + ".*";
        String date;
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE User_name =? AND Checked =?", new String[]{user_name,"0"});

        if (cursor.moveToFirst()) {
            for(int i =0;i<cursor.getCount();i++) {
                cursor.moveToPosition(i);
                date =cursor.getString(cursor.getColumnIndex("DAY"));
                String id = cursor.getString(cursor.getColumnIndex("ID"));
                if (Pattern.matches(pattern, date)) {
                    a.add(id);
                }
            }
            cursor.close();
            return a;
        } else {
            cursor.close();
            return a;
        }
    }

    static public void initDateofFirstLogin(){

        Calendar calendar = Calendar.getInstance();



        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH)+1;

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String date = year + "-" + month + "-" + day;
        MainActivity.INSERT_List(String.valueOf(9999),"Сделать",imgIds[3].toString(),"5" );
        MainActivity.INSERT_List(String.valueOf(9998),"Напоминания",imgIds[0].toString(),"3" );
        MainActivity.INSERT_List(String.valueOf(9997),"Лист покупок",imgIds[5].toString(),"6" );


        MainActivity.INSERT("Сделать",String.valueOf(8999),"Все напоминания храняться здесь"," "," ");
        MainActivity.INSERT("Сделать",String.valueOf(8998),"Выполнить задание можно кликнув по нему"," "," ");
        MainActivity.INSERT("Сделать",String.valueOf(8997),"Кликните еще раз чтобы убрать статус выполнения"," "," ");
        MainActivity.INSERT("Сделать",String.valueOf(8996),"Долгое нажатие откроет описание","Описание здесь"," ");
        MainActivity.INSERT("Сделать",String.valueOf(8995),"В правом верхнем углу можно открыть выполненые задачи"," "," ");

        MainActivity.INSERT("Напоминания",String.valueOf(8989),"Сходить в ресторан"," ",date+"-17-0");
        MainActivity.INSERT("Напоминания",String.valueOf(8988),"Вернуть книгу"," "," ");
        MainActivity.INSERT("Напоминания",String.valueOf(8987),"Постирать одежду"," ",date+"-19-0");

        MainActivity.INSERT("Лист покупок",String.valueOf(8969),"Молоко"," "," ");
        MainActivity.INSERT("Лист покупок",String.valueOf(8968),"Яйца"," "," ");
        MainActivity.INSERT("Лист покупок",String.valueOf(8967),"Хлеб"," "," ");
        MainActivity.INSERT("Лист покупок",String.valueOf(8966),"Рыба"," "," ");

        Insert_Schedule("999",date,""+9,""+10,"Встретиться с другом","",""+0);
        Insert_Schedule("998",date,""+11,""+12,"Конференция в зуме","",""+2);
        Insert_Schedule("997",date,""+13,""+14,"Созвон с клиентом","",""+4);
        Insert_Schedule("996",date,""+15,""+17,"Проектирование проекта","",""+1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");// HH:mm:ss

    }

}