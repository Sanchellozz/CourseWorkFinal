package com.example.chronus.TimeLine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TimeLineFragment extends Fragment {
    private static final String ARG_SHOW_TEXT = "text";
    private  String mContentText;

    private View view;

    List<TimeData> list = new ArrayList<>();


    TimeAdapter adapter;
    public TimeLineFragment() {
        // Required empty public constructor
    }


    public static TimeLineFragment newInstance(String param1){
        TimeLineFragment fragment = new TimeLineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_recyclerview, container, false);
        //init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //init();
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }



    private void  init(){
        RecyclerView rlView = (RecyclerView) view.findViewById(R.id.activity_rlview);

        rlView.setItemViewCacheSize(40);

        initData();

        TimeComparator comparator = new TimeComparator();
        Collections.sort(list, comparator);

        rlView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TimeAdapter((MainActivity) getActivity(), list);

        rlView.setAdapter(adapter);



    }


    private void initData() {
        list.clear();
        Calendar calendar = Calendar.getInstance();


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String today_day = year + "-" + month + "-" + day;


        List<String> reminder_today_id= new ArrayList<String>();
        reminder_today_id = MainActivity.find_ID_By_DAY(today_day);
        Iterator iterator=reminder_today_id.iterator();
        String reminder_title, reminder_time;
        while (iterator.hasNext()){
            String id = iterator.next().toString();
            Log.d("reminder-id",id);
            reminder_title = MainActivity.Get_Title_by_ID(id);
            reminder_time = MainActivity.Get_Day_by_ID(id);
            list.add(new TimeData(TimeFormat.toDate(reminder_time),reminder_title,""));
        }
        List<String> calendar_today_id= new ArrayList<String>();
        calendar_today_id = MainActivity.find_ID_By_date(today_day);
        Iterator iterator_cal=calendar_today_id.iterator();
        String cal_title, cal_time;
        while (iterator_cal.hasNext()){
            String id = iterator_cal.next().toString();
            cal_title = MainActivity.get_Title_In_Schedule(id);
            String date_cal = today_day +"-"+ String.valueOf(MainActivity.get_StartTime_In_Schedule(id))+"-0";
            cal_time = date_cal;
            list.add(new TimeData(TimeFormat.toDate(cal_time),"",cal_title));
        }

        if(MainActivity.user_name.equals("admin")){
            list.add(new TimeData(TimeFormat.toDate(today_day+"-12-00"),"Мероприятия созданные в календаре отображаются здесь",""));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-14-00"),"","Созданные напоминания тоже отображаются здесь"));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-19-30"),"Посмотреть банковский счет",""));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-12-05"),"Обсудить проект с начальником",""));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-12-05"),"Вернуть долг",""));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-19-30"),"","Сходить в спортивный зал"));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-20-00"),"","Провести эксперимент"));
            list.add(new TimeData(TimeFormat.toDate(today_day+"-24-00"),"","Лечь спать"));

        }

    }
}
