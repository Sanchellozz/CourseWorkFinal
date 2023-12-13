package com.example.chronus.Reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemindersFragment extends Fragment {

    private View view;
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private TextView edit_tv;
    private TextView addlist_tv;
    private TextView delete_tv;
    private ImageView search_iv;
    private EditText search_et;
    private Integer[] imgIds = new Integer[]{R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3,
            R.mipmap.lise_icon4, R.mipmap.lise_icon5, R.mipmap.lise_icon6,R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3};

    private int choose_img = R.drawable.radio_unselected;
    private int inf = R.drawable.ino_img;
    public ListView listView;
    public ArrayList<Integer> selseted_num = new ArrayList<Integer>();
    private RelativeLayout.LayoutParams params_lv;
    private int lastID = 0;
    private int Temp_Color;

    public RemindersFragment() {
        // Required empty public constructor
    }

    public static RemindersFragment newInstance(String param1){
        RemindersFragment fragment = new RemindersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.layout_reminders,container, false);

        String agrs1="text";
        if (getArguments() != null){
            Bundle bundle = getArguments();
            agrs1 = bundle.getString(ARG_SHOW_TEXT);
        }

        init();
        return view;
    }
    public void getListView(){

        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < MainActivity.get_ListCount(); i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("list_choose",choose_img);
            showitem.put("list_img", MainActivity.Show_List_Color_By_ID(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_name", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_num", MainActivity.Show_List_number(MainActivity.ShowLineID_inList(i)));
            listitem.add(showitem);
        }
        SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.layout_listview_a, new String[]{"list_choose","list_img", "list_name", "list_num"},
                new int[]{R.id.choose_img,R.id.list_img, R.id.list_name, R.id.list_num});
        listView = (ListView) view.findViewById(R.id.reminder_list);
        listView.setAdapter(myAdapter);

        RelativeLayout.LayoutParams params_set = params_lv;

        params_set.height = listView.getAdapter().getCount() * (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
        listView.setLayoutParams(params_set);
    }
    public void init(){

        search_iv = view.findViewById(R.id.search_iv);
        search_et = view.findViewById(R.id.search_et);
        search_et.clearFocus();

        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < MainActivity.get_ListCount(); i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("list_choose",choose_img);
            showitem.put("list_img", MainActivity.Show_List_Color_By_ID(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_name", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_num", MainActivity.Show_List_number(MainActivity.ShowLineID_inList(i)));
            listitem.add(showitem);
        }

        final Integer List_ID_number = listitem.size();

        SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.layout_listview_a, new String[]{"list_choose","list_img", "list_name", "list_num"},
                new int[]{R.id.choose_img,R.id.list_img, R.id.list_name, R.id.list_num});
        listView = (ListView) view.findViewById(R.id.reminder_list);
        listView.setAdapter(myAdapter);

        params_lv = (RelativeLayout.LayoutParams) listView.getLayoutParams();
        RelativeLayout.LayoutParams params_set =params_lv;
        params_set.height= (int) listView.getAdapter().getCount() * params_lv.height ;
        listView.setLayoutParams(params_set);
        onClickListView();
        setListener();

        lastID = lastID + MainActivity.get_ListCount();
    }
    private void setListener(){


        edit_tv = view.findViewById(R.id.edit_tv);
        edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_tv.getText().equals("Edit")){
                    edit_tv.setText("Done");
                    delete_tv.setVisibility(View.VISIBLE);
                    edit_tv.getPaint().setFakeBoldText(true);



                    final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < MainActivity.get_ListCount(); i++) {
                        Map<String, Object> showitem = new HashMap<String, Object>();
                        showitem.put("list_choose",choose_img);
                        showitem.put("list_img", MainActivity.Show_List_Color_By_ID(MainActivity.ShowLineID_inList(i)));
                        showitem.put("list_name", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(i)));
                        showitem.put("list_num", MainActivity.Show_List_number(MainActivity.ShowLineID_inList(i)));
                        showitem.put("inf", inf);
                        listitem.add(showitem);
                    }

                    SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.layout_listview, new String[]{"list_choose","list_img", "list_name", "list_num","inf"},
                            new int[]{R.id.choose_img,R.id.list_img, R.id.list_name, R.id.list_num,R.id.inf});
                    listView = (ListView) view.findViewById(R.id.reminder_list);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            mCallback.onEventsSelected(position);
                            if(edit_tv.getText().equals("Done")){
                                if(!selseted_num.contains(position)){
                                    selseted_num.add(position);
                                    ImageView choose_img = view.findViewById(R.id.choose_img);
                                    choose_img.setImageResource(R.drawable.delete);
                                    choose_img.setPadding(5,5,5,5);

                                }else {
                                    selseted_num.remove(selseted_num.indexOf(position));

                                    ImageView choose_img = view.findViewById(R.id.choose_img);
                                    choose_img.setImageResource(R.drawable.radio_unselected);
                                    choose_img.setPadding(0,0,0,0);
                                }
                            }
                        }
                    });
                }else{
                    edit_tv.setText("Edit");
                    delete_tv.setVisibility(View.INVISIBLE);

                    edit_tv.getPaint().setFakeBoldText(false);
                    selseted_num.clear();
                    onClickListView();
                    getListView();
                }
            }
        });

        delete_tv = view.findViewById(R.id.delete_tv);
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog alertDialog = builder
                        .setTitle("Alert")
                        .setMessage("Вы действительно хотите удалить список？")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int n) {

                            }
                        })
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int n) {
                                if(selseted_num.isEmpty()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    AlertDialog alertDialog = builder
                                            .setTitle("Alert")
                                            .setMessage("Не выбран список")
                                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int n) {

                                                }
                                            }).create();
                                    alertDialog.show();
                                }else {
                                    MediaPlayer mMediaPlayer;
                                    mMediaPlayer =  MediaPlayer.create(getContext(),R.raw.delete);
                                    mMediaPlayer.start();
                                    for(int i=(selseted_num.size()-1);i>=0;i--){
                                        int line = selseted_num.get(i);
                                        MainActivity.DELETE_LIST_By_ID(MainActivity.ShowLineID_inList(line));
                                        Log.d("Delete",String.valueOf(line));
                                    }
                                    edit_tv.setText("Edit");
                                    delete_tv.setVisibility(View.INVISIBLE);

                                    edit_tv.getPaint().setFakeBoldText(false);
                                    selseted_num.clear();
                                    onClickListView();
                                    getListView();

                                }

                            }
                        }).create();
                alertDialog.show();



            }
        });

        addlist_tv = view.findViewById(R.id.addlist_tv);
        addlist_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Создать новый список");

                final View add_view = LayoutInflater.from(getActivity()).inflate(R.layout.reminder_add_layout,null,false);
                builder.setView(add_view);
                final EditText et = add_view.findViewById(R.id.add_et);
                final ImageView imageView = add_view.findViewById(R.id.add_im);
                RadioGroup group = add_view.findViewById(R.id.add_rbg);
                Temp_Color = 0;
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Integer id_rb[] = new Integer[]{R.id.rb1,R.id.rb2,R.id.rb3,R.id.rb4,R.id.rb5,R.id.rb6};
                        for(int i = 0;i<id_rb.length;i++){
                            if(checkedId == id_rb[i]){
                                Temp_Color = i;
                                imageView.setImageResource(imgIds[i]);

                            }
                        }
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MediaPlayer mMediaPlayer;
                        mMediaPlayer =  MediaPlayer.create(getContext(),R.raw.confirm_up);
                        mMediaPlayer.start();

                        Integer list_id_number = ++lastID;

                        MainActivity.INSERT_List(list_id_number.toString(),et.getText().toString(),imgIds[Temp_Color].toString(),"0" );
                        Toast.makeText(getActivity(), "успешно создано! "+et.getText().toString(), Toast.LENGTH_LONG).show();

                        getListView();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create();
                builder.show();
            }
        });

        search_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_et.setText("");
                search_et.setTextColor(Color.parseColor("#000000"));
            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView tv, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    Log.d("tag:",search_et.getText().toString());
                    if(tv.getText()==null){
                        Toast.makeText(getActivity(), "Информация о запросе не введена", Toast.LENGTH_LONG).show();
                    }else{

                    }

                    return true;
                }
                return false;

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


        getListView();
    }



    OnTitleSelectedListener mCallback;
    public interface OnTitleSelectedListener{
        public void onEventsSelected(int position);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            mCallback = (OnTitleSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "OnTitleSelectedListener");
        }
    }
    public void onClickListView(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                mCallback.onEventsSelected(position);
                edit_tv = view.findViewById(R.id.edit_tv);
                if(edit_tv.getText().equals("Edit")){
                    Intent intent = new Intent(getContext(), ReminderItemsActivity.class);
                    intent.putExtra("Line", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(position)));
                    intent.putExtra("ListId",MainActivity.ShowLineID_inList(position));
                    TextView tv_back = view.findViewById(R.id.item_back);
                    startActivity(intent);
                }

            }
        });
    }

}
