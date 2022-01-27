package com.example.dvir_game_try;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    final String GUIDE_FRAGMENT_TAG = "guide_fragment";

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
//                Stage stage = new Stage(getResources(),1, 2, 3, 3);
//                GuideFragment1 fragment = GuideFragment1.newInstance(stage);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.add(R.id.root_container, fragment, GUIDE_FRAGMENT_TAG);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });


        MusicPlayer.getInstance().initialize(this);
        MusicPlayer.getInstance().play(true);







    }
}