package com.example.dvir_game_try;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    private Paint paint;
    private SharedPreferences sp;
    private TreeMap<Integer, String> topTenRecord = new TreeMap<>();
    private ArrayList<Covid> covids = new ArrayList<>();
    private ArrayList<People> peoples = new ArrayList<>();
    private ArrayList<People> enemies = new ArrayList<>();
    private int[] enemiesArr;

    List<Integer> picPeople = new ArrayList<>(Arrays.asList(
            R.drawable.people_1, R.drawable.people_2, R.drawable.people_3, R.drawable.people_4,
            R.drawable.people_5, R.drawable.people_6, R.drawable.people_7, R.drawable.people_8, R.drawable.people_9,
            R.drawable.people_10, R.drawable.people_11, R.drawable.people_12, R.drawable.people_13,
            R.drawable.people_14, R.drawable.people_15, R.drawable.people_16, R.drawable.people_18, R.drawable.people_19,
            R.drawable.people_20, R.drawable.people_21));
    private int lives = 3;
    private Random random;
    private GameActivity activity;
    public static float screenRatioX, screenRatioY;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY, int[] enemiesArr) {
        super(activity);

        this.activity = activity;
        sp = activity.getSharedPreferences("game_10_records", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        random = new Random();

        this.enemiesArr = enemiesArr;

        for (Integer i : enemiesArr) {
            picPeople.remove(i);
        }

//        for (int i = 0; i < 2; i++) {
//            covids.add(new Covid(getResources(), R.drawable.covid));
//        }
        for (int i = 0; i < 2; i++) {
            int randomPeople = random.nextInt(picPeople.size());
            peoples.add(new People(getResources(), picPeople.get(randomPeople)));
            picPeople.remove(randomPeople);
        }
        for (int i = 0; i < 4; i++) {
            enemies.add(new People(getResources(), enemiesArr[random.nextInt(3)]));
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {

        background1.x -= 10;
        background2.x -= 10;

        if (background1.x + background1.background.getWidth() < 0) { //all the image outside the screen
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) { //all the image outside the screen
            background2.x = screenX;
        }
//        if (player.isGoingUp) {
//            player.y -= 200 * screenRatioY;
//            player.isGoingUp = false;
//        }
//        if (player.y < 0) {
//            player.y = 0;
//        }

//        for (int i = 0; i < covids.size(); i++) {
//            covids.get(i).x -= covids.get(i).speed;
//
//            if (covids.get(i).x + covids.get(i).width < 0) {
//
//                int bound = (int) (30 * screenRatioX);
//                covids.get(i).speed = random.nextInt(bound);
//
//                if (covids.get(i).speed < 10 * screenRatioX) {
//                    covids.get(i).speed = (int) (10 * screenRatioX);
//                }
//                covids.get(i).x = screenX;
//                covids.get(i).y = random.nextInt(screenY - covids.get(i).height);
//            }
//
//            updateLives = covids.get(i).isInfected(player, lives, score);
//            if (lives != updateLives) {
//                covids.remove(covids.get(i));
//                covids.add(new Covid(getResources(), R.drawable.covid));
//                lives = updateLives;
//                if (lives == 0) {
//                    isGameOver = true;
//
//                }
//                return;
//            }
//        }

        for (People people : peoples) {
            people.x -= people.speed;

            if (people.x + people.width < 0) {

                int bound = (int) (30 * screenRatioX);
                people.speed = random.nextInt(bound);

                if (people.speed < 10 * screenRatioX) {
                    people.speed = (int) (10 * screenRatioX);
                }
                people.x = screenX;
                people.y = random.nextInt(screenY - people.height);
                people.wasCovered = false;
                people.Covered(false);

            }
        }
        for (People people : enemies) {
            people.x -= people.speed;

            if (people.x + people.width < 0) {
                if (!people.firstTimeInit) {
                    if (!people.wasCovered) {
                        lives--;
                        if (lives == 0) {
                            isGameOver = true;
                            return;
                        }
                    }
                }


                int bound = (int) (30 * screenRatioX);
                people.speed = random.nextInt(bound);

                if (people.speed < 10 * screenRatioX) {
                    people.speed = (int) (10 * screenRatioX);
                }
                people.x = screenX;
                people.y = random.nextInt(screenY - people.height);
                people.wasCovered = false;
                people.firstTimeInit = false;
                people.Covered(false);

            }
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

//            for (Covid covid : covids) {
//                canvas.drawBitmap(covid.getObject(), covid.x, covid.y, paint);
//            }
            for (People people : peoples) {
                canvas.drawBitmap(people.getObject(), people.x, people.y, paint);
            }
            for (People people : enemies) {
                canvas.drawBitmap(people.getObject(), people.x, people.y, paint);
            }
            Bitmap redLive = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
            redLive = Bitmap.createScaledBitmap(redLive, 100, 100, false);

            for (int i = 0; i < lives; i++) {
                canvas.drawBitmap(redLive, (100 * (i + 1)) * screenRatioX, 100 * screenRatioY, paint);
            }
            canvas.drawText(score + "", screenX / 2f, 164, paint);
            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                if (score > 0) saveIfHighScore();
                else{
                    waitBeforeExiting();
                }
                return;
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(2000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        Map<String, ?> keys = sp.getAll();
//        for (Map.Entry<String, ?> entry : keys.entrySet()) {
//            SharedPreferences.Editor editor = sp.edit();
//            editor.remove(entry.getKey());
//            editor.apply();
//            }
        System.out.println(sp.getAll().size());
        if (sp.getAll().size() < 10) {
                openDialog();
        }
        else {
            int minValue = sp.getInt("1", 0);
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                if (minValue > Integer.valueOf((int) entry.getValue())) {
                    minValue = Integer.valueOf((int) entry.getValue());
                }
            }
            if (score > minValue) {
                openDialog();
            }
        }
    }

    private void openDialog() {
        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View dialogView = activity.getLayoutInflater().inflate(R.layout.save_record_dialog, null);
                builder.setView(dialogView).setCancelable(false).show();

                Button saveToRecord = dialogView.findViewById(R.id.save_to_record_table);
                EditText nameEt = dialogView.findViewById(R.id.edit_name);
                TextView yourScore = dialogView.findViewById(R.id.your_score);
                yourScore.setText(yourScore.getText().toString() + " " + score);

                saveToRecord.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("lal");
                        Intent intent = new Intent(activity, RecordActivity.class);
                        intent.putExtra("name",nameEt.getText().toString());
                        intent.putExtra("score", String.valueOf(score));
                        System.out.println("l");
                        activity.startActivity(intent);
                    }
                });
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(17); // 16 frames in second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //resume or start the game
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start(); //calling the run function above
    }

    //pause the game
    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (People people : enemies) {
                    if (people.getCollisionShape().contains(x, y)) {
                        if (!people.wasCovered) {
                            people.Covered(true);
                            score++;
                        }
                    }
                }
                for (People people : peoples) {
                    if (people.getCollisionShape().contains(x, y)) {
                        if (!people.wasCovered) {
                            if (score > 0) score--;
                        }
                    }
                }
        }
        return true;
    }
}
