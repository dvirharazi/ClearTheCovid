package com.example.dvir_game_try;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);

    }

    @Override
    protected void onPause() {
        MusicPlayer.getInstance().pause(false);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MusicPlayer.getInstance().play(false);
    }
}