package com.example.dvir_game_try;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static float screenRatioX, screenRatioY;
    private GameView gameView;
    private ArrayList<Integer> enemiesPic = new ArrayList<>();
    private Stage stage;
    private SharedPreferences sp;
    private static Boolean isMusicPlaying = false;
    int level,numberOfEnemies,numberOfPeople,time;
    final String GUIDE_FRAGMENT_TAG = "guide_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        screenRatioX = (float) (1920f / point.x);
        screenRatioY = (float) (1080f / point.y);


        stage = new Stage(getResources(),1, 2, 3, 1, 10,20);
        gameView = new GameView(this, point.x, point.y, stage);

        setContentView(gameView);

        if(!MusicPlayer.getInstance().getIsPaused()){
            MusicPlayer.getInstance().play(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}