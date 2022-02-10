package com.example.dvir_game_try;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button playBtn = findViewById(R.id.play);
        Button recordBtn = findViewById(R.id.move_to_record);

        ImageView covid1 = findViewById(R.id.covid1);
        ImageView covid2 = findViewById(R.id.covid2);
        ImageView covid3 = findViewById(R.id.covid3);

        Animation moveAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.covid_move_anim);
        covid1.startAnimation(moveAnim);
        covid2.startAnimation(moveAnim);
        covid3.startAnimation(moveAnim);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        View.OnTouchListener shakeButtonListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
            }
        };


        recordBtn.setOnTouchListener(shakeButtonListener);
        playBtn.setOnTouchListener(shakeButtonListener);


        MusicPlayer.getInstance().initialize(this);
        MusicPlayer.getInstance().play(true);

        ImageButton imageButton = findViewById(R.id.music_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MusicPlayer.getInstance().getIsPaused()){
                    ((ImageButton)v).setSelected(false);
                    MusicPlayer.getInstance().play(true);
                }else{
                    ((ImageButton)v).setSelected(true);
                    MusicPlayer.getInstance().pause(true);
                }

            }
        });
    }
}