package com.example.ClearTheCovid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import Fragments.GuideFragment_1;

public class MainActivity extends BaseActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button playBtn = findViewById(R.id.play);
        Button recordBtn = findViewById(R.id.move_to_record);
        @SuppressLint("CutPasteId") Button moveToTutorial = findViewById(R.id.move_to_tutorial);

        ImageView covid1 = findViewById(R.id.covid1);
        ImageView covid2 = findViewById(R.id.covid2);
        ImageView covid3 = findViewById(R.id.covid3);

        @SuppressLint("CutPasteId") Button moveTutorial = findViewById(R.id.move_to_tutorial);
        moveTutorial.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().add(R.id.fragment_containerplay, new GuideFragment_1(), null).addToBackStack("First Guidance").commit());



        Animation moveAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.covid_move_anim);
        covid1.startAnimation(moveAnim);
        covid2.startAnimation(moveAnim);
        covid3.startAnimation(moveAnim);

        recordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);
        });

        playBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        View.OnTouchListener shakeButtonListener = (v, event) -> {
            final Animation animShake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_animation);
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    v.startAnimation(animShake);
                    break;
                case MotionEvent.ACTION_UP:
                    v.clearAnimation();
                    break;
            }
            return false;
        };


        recordBtn.setOnTouchListener(shakeButtonListener);
        playBtn.setOnTouchListener(shakeButtonListener);
        moveToTutorial.setOnTouchListener(shakeButtonListener);

        ImageButton imageButton = findViewById(R.id.music_btn);
        imageButton.setOnClickListener(v -> {
            if(MusicPlayer.getInstance().getIsPaused()){
                v.setSelected(true);
                MusicPlayer.getInstance().play(true);
            }else{
                v.setSelected(false);
                MusicPlayer.getInstance().pause(true);
            }
        });

        MusicPlayer.getInstance().initialize(this);
        imageButton.setSelected(!MusicPlayer.getInstance().getIsPaused());
    }
}