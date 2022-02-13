package com.example.ClearTheCovid;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

public class GameActivity extends BaseActivity {

//    public static float screenRatioX, screenRatioY;
    private GameView gameView;
    private ArrayList<Integer> enemiesPic = new ArrayList<>();
    private Stage stage;
    private SharedPreferences sp;
    private static Boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        stage = new Stage(this, getResources(),1, 2, 3, 60, 10,20, point.x,point.y);
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