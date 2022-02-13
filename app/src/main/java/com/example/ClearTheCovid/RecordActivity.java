package com.example.ClearTheCovid;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordActivity extends BaseActivity {
    SharedPreferences sp;
    List<Record> topRecords = new ArrayList<>();
    Record record;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_record);
        sp = getSharedPreferences("game_10_records", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (!MusicPlayer.getInstance().getIsPaused()) {
            MusicPlayer.getInstance().play(true);
        }

        Button backToMenu = findViewById(R.id.back_to_menu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        View.OnTouchListener shakeButtonListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Animation animShake = AnimationUtils.loadAnimation(RecordActivity.this, R.anim.btn_animation);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.startAnimation(animShake);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.clearAnimation();
                        break;
                }
                return false;
            }
        };

        backToMenu.setOnTouchListener(shakeButtonListener);

        String name = getIntent().getStringExtra("name");
        String score = getIntent().getStringExtra("score");


        if (name != null) {
            Record record = new Record(name, score, "1");
            System.out.println(record);
            topRecords.add(record);

            //get all the records from memory
            getRecordsFromSharedPreferences(sp, topRecords);

            topRecords.sort((o1, o2) -> o2.compareTo(o1));

            //set id for each record
            for (int i = 0; i < topRecords.size(); i++) {
                topRecords.get(i).setId(String.valueOf(i + 1));
            }

            //delete the last record if there more then 10 records
            if (topRecords.size() > 10) {
                topRecords.remove(topRecords.get(topRecords.size()-1));
            }
            // write to memory
            for (int i = 0; i < topRecords.size(); i++) {
                editor.putString("id" + i, Integer.valueOf(topRecords.get(i).getPoint()) + " " + topRecords.get(i).getName());
            }
            editor.apply();

        } else {
            getRecordsFromSharedPreferences(sp, topRecords);
        }
        topRecords.sort((o1, o2) -> o2.compareTo(o1));

        for (int i = 0; i < topRecords.size(); i++) {
            topRecords.get(i).setId(String.valueOf(i + 1));
        }

        ListView recordsView = findViewById(R.id.record_list);
        RecordAdapter recordAdapter = new RecordAdapter(topRecords, this);
        recordsView.setAdapter(recordAdapter);

    }

    private void getRecordsFromSharedPreferences(SharedPreferences sp, List<Record> topRecords){
        Map<String, ?> keys = sp.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            int i = 0;
            String infoRecord = (String) entry.getValue();
            String[] info = infoRecord.split("\\s+");
            record = new Record(info[1], info[0], "");
            topRecords.add(record);
        }
    }
}