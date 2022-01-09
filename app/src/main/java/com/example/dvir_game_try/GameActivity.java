package com.example.dvir_game_try;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private ArrayList<Integer> enemies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);


        Bundle bundle = getIntent().getExtras();
        int [] enemiesArr = bundle.getIntArray("enemies");

        gameView = new GameView(this,point.x,point.y, enemiesArr);

        setContentView(gameView);
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