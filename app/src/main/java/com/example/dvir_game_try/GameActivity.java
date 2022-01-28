package com.example.dvir_game_try;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static float screenRatioX, screenRatioY;
    private GameView gameView;
    private ArrayList<Integer> enemiesPic = new ArrayList<>();
    private Stage stage;
    private SharedPreferences sp;
    int level,numberOfEnemies,numberOfPeople,time;
    final String GUIDE_FRAGMENT_TAG = "guide_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        GuideFragment1 fragment = GuideFragment1.newInstance(stage.updateEnemiesPicArray());
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.root_container, fragment, GUIDE_FRAGMENT_TAG);
//        transaction.addToBackStack(null);
//        transaction.commit();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        sp = getSharedPreferences("game_10_records", MODE_PRIVATE);
//        level = sp.getInt("level" , 0);
//        numberOfEnemies = sp.getInt("numberOfEnemies" , 1);
//        numberOfPeople = sp.getInt("numberOfPeople" , 1);
//        time = sp.getInt("time" , 1000);




//        Bundle bundle = getIntent().getExtras();
//        ArrayList<Integer> enemiesArr = bundle.getIntegerArrayList("enemiesPic");
//        ArrayList<Integer> peoplesArr = bundle.getIntegerArrayList("peoplesPic");
//        int level = bundle.getInt("level");
//        int time = bundle.getInt("time");

//        stage.setPeoplePic(peoplesArr);
//        stage.setEnemiesPic(enemiesArr);
//        stage.setTime(time);
//        stage.setLevel(level);

        screenRatioX = (float) (1920f / point.x);
        screenRatioY = (float) (1080f / point.y);


        stage = new Stage(getResources(),0, 2, 3, 10, 10,20);
//        gameView = new GameView(this,point.x,point.y, enemiesArr, stage);
        gameView = new GameView(this, point.x, point.y, stage);

        setContentView(gameView);

        MusicPlayer.getInstance().play(true);
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