package com.BSTech.stopwatch;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView, reset, lap ;
    private ImageView start;
    private long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    private Handler handler;
    private int Seconds, Minutes, MilliSeconds ;
    private ListView listView ;
    private String[] ListElements = new String[] {  };
    private List<String> ListElementsArrayList ;
    private ArrayAdapter<String> adapter ;
    private boolean process = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        start = (ImageView) findViewById(R.id.play);
        reset = (TextView)findViewById(R.id.reset);
        lap = (TextView)findViewById(R.id.lap) ;
        listView = (ListView)findViewById(R.id.listview1);

        reset.setVisibility(View.INVISIBLE);
        lap.setVisibility(View.INVISIBLE);


        handler = new Handler() ;

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lap.setVisibility(View.VISIBLE);

                if (process){

                    start.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    reset.setVisibility(View.INVISIBLE);


                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    process = false;

                }else{
                    TimeBuff += MillisecondTime;

                    start.setImageResource(R.drawable.ic_pause_black_24dp);
                    reset.setVisibility(View.VISIBLE);

                    handler.removeCallbacks(runnable);
                    process = true;
                }
            }
        });



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset.setVisibility(View.INVISIBLE);
                lap.setVisibility(View.INVISIBLE);

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                textView.setText("00:00:00");

                ListElementsArrayList.clear();
                start.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

}