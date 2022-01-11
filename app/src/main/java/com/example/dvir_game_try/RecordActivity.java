package com.example.dvir_game_try;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
    SharedPreferences sp;
    List<Record> topRecords = new ArrayList<>();
    Record record;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        sp = getSharedPreferences("game_10_records", MODE_PRIVATE);

        String name = getIntent().getStringExtra("name");
        String score = getIntent().getStringExtra("score");


        if (name != null) {
            Record record = new Record(name, score, "1");
            System.out.println(record);
            topRecords.add(record);


            Map<String, ?> keys = sp.getAll();

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                if (keys.size() > 0) {
                    String infoRecord = (String) entry.getValue();

                    System.out.println(infoRecord);

                    String[] info = infoRecord.split("\\s+");
                    record = new Record(info[1], info[0], entry.getKey());
                    topRecords.add(record);
                }
            }
            topRecords.sort((o1, o2) -> {
                return o2.compareTo(o1);
            });

            for (int i = 0; i < topRecords.size(); i++) {
                topRecords.get(i).setId(String.valueOf(i + 1));
            }

            if (topRecords.size() > 10) {
                topRecords.remove(topRecords.get(topRecords.size()));
            }

            for (int i = 0; i < topRecords.size(); i++) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("id" + i, Integer.valueOf(topRecords.get(i).getPoint()) + " " + topRecords.get(i).getName());
                editor.apply();
            }
        } else {
            Map<String, ?> keys = sp.getAll();
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                if (keys.size() > 0) {
                    String infoRecord = (String) entry.getValue();
                    System.out.println(infoRecord);
                    String[] info = infoRecord.split("\\s+");
                    record = new Record(info[1], info[0], "1");
                    topRecords.add(record);
                }
            }
        }
        topRecords.sort((o1, o2) -> {
            return o2.compareTo(o1);
        });

        for (int i = 0; i < topRecords.size(); i++) {
            topRecords.get(i).setId(String.valueOf(i + 1));
        }

        ListView recordsView = findViewById(R.id.record_list);
        RecordAdapter recordAdapter = new RecordAdapter(topRecords, this);
        recordsView.setAdapter(recordAdapter);

    }
}