package com.example.chronus.Reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ReminderItemsActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;

    int mCurrentPosition = -1;
    private int imgIds =  R.drawable.radio_unselected;
    private int imgAdd =  R.drawable.item_add;
    private int seleted_img = R.drawable.radio_selected;
    private ImageView mPopupMenu;
    public static String get_Type;
    public String ListId;
    public String  Is_Finished;

    public int Line;

    public  static List<String> list_unfinished;
    public  static List<String> list_finished;
    public static int []clicktime_unfinished =new int[100];
    public static int []clicktime_finished =new int[100];

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setEnterTransition(slide);
        setContentView(R.layout.layout_reminders_item);
        context = this;

        Intent intent = getIntent();
        get_Type = intent.getStringExtra("Line");
        ListId = intent.getStringExtra("ListId");

        TextView textView =(TextView)findViewById(R.id.item_list_name);
        textView.setText(get_Type);
        for(int i=0;i<100;i++)
            clicktime_unfinished [i]=0;
        for(int i=0;i<100;i++)
            clicktime_finished [i]=0;
        list_unfinished =new ArrayList<String>();
        list_finished =new ArrayList<String>();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.item_back:
                this.finish();
                break;
        }
    }
    @Override
    public void onStart(){
        super.onStart();

        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout item_ll = findViewById(R.id.item_ll);
                item_ll.setBackgroundColor(Color.BLACK);
                RelativeLayout item_rl = findViewById(R.id.item_rl);
                item_rl.setVisibility(View.VISIBLE);
            }
        },500);

    }
    @Override
    public void onResume(){
        super.onResume();
        LinearLayout item_ll = findViewById(R.id.item_ll);
        item_ll.setBackgroundColor(Color.WHITE);
        RelativeLayout item_rl = findViewById(R.id.item_rl);
        item_rl.setVisibility(View.INVISIBLE);
        getListView("0");
        Log.d("state","onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        LinearLayout item_ll = findViewById(R.id.item_ll);
        item_ll.setBackgroundColor(Color.WHITE);
        RelativeLayout item_rl = findViewById(R.id.item_rl);
        item_rl.setVisibility(View.INVISIBLE);
        Log.d("state","onPause");
        Iterator iterator= list_unfinished.iterator();
        while (iterator.hasNext()) {
            String title =iterator.next().toString();
            MainActivity.SetFinished_By_Title(title);
        }
        Iterator iterator2= list_finished.iterator();
        while (iterator2.hasNext()) {
            String title2 =iterator2.next().toString();
            MainActivity.Set_UnFinished_By_Title(title2);
        }

        for(int i=0;i<100;i++)
            clicktime_unfinished [i]=0;
        for(int i=0;i<100;i++)
            clicktime_finished [i]=0;
        list_unfinished.clear();
        list_finished.clear();
    }
    private void getListView(String t){
        Is_Finished =t;
        if(t.equals("0"))
            Toast.makeText(MainActivity.mainActivity,"page for to-do items",Toast.LENGTH_SHORT).show();
        else{
            Toast.makeText(MainActivity.mainActivity,"page for the finished items",Toast.LENGTH_SHORT).show();
        }
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < MainActivity.getCount_By_Type(get_Type,t); i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("item_name", MainActivity.ShowLineTitle_In_Type(i,get_Type,t));
            if(t == "0"){
                showitem.put("item_img", imgIds);
                showitem.put("item_name", MainActivity.ShowLineTitle_In_Type(i,get_Type,"0"));

            }else{
                showitem.put("item_img",seleted_img);
                showitem.put("item_name", MainActivity.ShowLineTitle_In_Type(i,get_Type,"1"));

            }

            listitem.add(showitem);
        }

        Map<String, Object> showitem = new HashMap<String, Object>();
        showitem.put("item_img", imgAdd);
        showitem.put("item_name", "Add to-do item");
        listitem.add(showitem);
        SimpleAdapter myAdapter = new SimpleAdapter(context, listitem, R.layout.layout_item_view, new String[]{"item_img", "item_name"},
                new int[]{R.id.choose_img, R.id.item_name});
        ListView listView = (ListView) findViewById(R.id.item_list);
        listView.setAdapter(myAdapter);
    }
    private void get_Item(){

    }
    private void init() {
        Is_Finished ="0";

        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < MainActivity.getCount_By_Type(get_Type,"0"); i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("item_img", imgIds);
            showitem.put("item_name", MainActivity.ShowLineTitle_In_Type(i,get_Type,"0"));



            listitem.add(showitem);
        }
        final Integer number = MainActivity.getCount();

        Map<String, Object> showitem = new HashMap<String, Object>();
        showitem.put("item_img", imgAdd);
        showitem.put("item_name", "Add to-do item");
        listitem.add(showitem);
        SimpleAdapter myAdapter = new SimpleAdapter(context, listitem, R.layout.layout_item_view, new String[]{"item_img", "item_name"},
                new int[]{R.id.choose_img, R.id.item_name});
        ListView listView = (ListView) findViewById(R.id.item_list);
        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ImageView sel_img = view.findViewById(R.id.choose_img);
                TextView sel_tv = view.findViewById(R.id.item_name);




                if(sel_tv.getText().equals("Add to-do item")){

                    Log.d("id",Integer.toString(MainActivity.getCount()));
                    Intent intent = new Intent(context, ADD_DATA_Activity.class);
                    intent.putExtra("number",number);
                    intent.putExtra("type", get_Type);
                    startActivity(intent);
                } else if(Is_Finished=="0"){
                    Line = i;


                    if (clicktime_unfinished [Line] % 2 == 0) {
                        sel_img.setImageResource(seleted_img);
                        list_unfinished.add(MainActivity.ShowLineTitle_In_Type(Line, get_Type, Is_Finished));
                        clicktime_unfinished [Line]++;
                    } else {
                        sel_img.setImageResource(imgIds);

                        list_unfinished.remove(MainActivity.ShowLineTitle_In_Type(Line, get_Type, Is_Finished));
                        clicktime_unfinished [Line]++;
                    }
                }
                else if(Is_Finished=="1"){
                    Line = i;

                    if (clicktime_finished [Line] % 2 == 0) {
                        sel_img.setImageResource(imgIds);
                        list_finished.add(MainActivity.ShowLineTitle_In_Type(Line, get_Type, Is_Finished));
                        clicktime_finished [Line]++;
                    } else {
                        sel_img.setImageResource(seleted_img);


                        list_finished.remove(MainActivity.ShowLineTitle_In_Type(Line, get_Type, Is_Finished));

                        clicktime_finished [Line]++;
                    }
                }

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView sel_tv = view.findViewById(R.id.item_name);
                if(sel_tv.getText().equals("Add to-do item")){
                }else if(Is_Finished=="1"){
                }else{
                    MainActivity.Line = i;
                    MainActivity.Edit_ID = MainActivity.ShowLineID(MainActivity.Line);
                    String item_id = MainActivity.ShowLineID_inType(i,get_Type,"0");
                    Intent intent = new Intent(getApplicationContext(), Update_DATA_Activity.class);
                    intent.putExtra("item_id", item_id);
                    intent.putExtra("item_from_listname",get_Type);

                    startActivity(intent);
                }


                return true;
            }
        });
        mPopupMenu = findViewById(R.id.item_top_menu);
        mPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu();
            }
        });

        TextView back = findViewById(R.id.item_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.confirm_down);
                mMediaPlayer.start();
                finish();

            }
        });
        ImageView img = findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }

    private void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(this,mPopupMenu);
        if(getContext() == null){
            return ;
        }else {
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.reminders_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.rem_delete:
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            AlertDialog alertDialog = builder
                                    .setTitle("Hint")
                                    .setMessage("Вы действительно хотите удалить список?")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int n) {

                                        }
                                    })
                                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int n) {
                                            MainActivity.DELETE_LIST_By_ID(ListId);
                                            Log.d("delete list id",ListId);
                                            MediaPlayer mMediaPlayer;
                                            mMediaPlayer =  MediaPlayer.create(context,R.raw.delete);
                                            mMediaPlayer.start();
                                            finish();
                                        }
                                    }).create();
                            alertDialog.show();
                            return true;

                        case R.id.rem_finish:

                            if(menuItem.getTitle().equals("Возврат")){

                            }else{
                                getListView("1");
                                Toast.makeText(context,"all finished items",Toast.LENGTH_SHORT).show();
                            }

                            return true;
                        default:
                            //do nothing
                    }

                    return false;
                }
            });
            popupMenu.show();
        }
    }
    public void updateItemView(int position){
        Activity activity = this;
        mCurrentPosition = position;
    }

@Override
    public void onDestroy(){
    super.onDestroy();
    LinearLayout item_ll = findViewById(R.id.item_ll);
    item_ll.setBackgroundColor(Color.WHITE);
    RelativeLayout item_rl = findViewById(R.id.item_rl);
    item_rl.setVisibility(View.INVISIBLE);
}

}
