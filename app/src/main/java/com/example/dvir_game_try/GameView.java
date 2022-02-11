package com.example.dvir_game_try;

import static com.example.dvir_game_try.GameActivity.screenRatioX;
import static com.example.dvir_game_try.GameActivity.screenRatioY;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private final int screenX;
    private final int screenY;
    private int score = 0;
    private final Paint paint;
    private final SharedPreferences sp;
    private final Stage stage;
    Covid covid;
    static boolean coronaStage = false;
    Timer timer = new Timer();
    private int lives = 3;
    Bitmap pauseBtn;
    private final Random random;
    private final GameActivity activity;
    private final Background background1;
    private final float covidWidth;
    private final float covidHeight;

    public GameView(GameActivity activity, int screenX, int screenY, Stage stage) {
        super(activity);

        this.activity = activity;
        sp = activity.getSharedPreferences("game_10_records", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;

        background1 = new Background(screenX, screenY, getResources());

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        this.stage = stage;

        random = new Random();

        covid = new Covid(getResources(), new Integer[]{R.drawable.covid}, 10, 20);

        covidWidth = covid.getWidth();
        covidHeight = covid.getHeight();


    }

    @Override
    public void run() {

        timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = stage.getTime();

            public void run() {
                if (stage.getLevel() == 1) {
                    firstStageGuide();
                    goToNextStage();
                }
                if (i >= 0) {
                    i--;
                } else {
                    goToNextStage();
                    i = stage.getTime();
                }
            }
        };
        timer.schedule(task, 0, 1000);


        while (isPlaying) {
            update();
            draw();
            sleep();
        }
        timer.cancel();
    }

    private void goToNextStage() {
        if ((stage.getLevel() - 1) % 3 == 0) {
            if ((stage.getLevel() - 1) != 0) {
                coronaStage = true;
            }
        }

        if ((stage.getLevel() - 1) % 5 == 0) {
            pause();
            if ((stage.getLevel() - 1) != 0) {
                stage.setNumberOfEnemies(stage.getNumberOfEnemies() + 1);
                stage.setEnemiesPic(stage.updateEnemiesPicArray());
                stage.updateEnemiesPicArray();
                stage.updateEnemiesArray();

                stage.setNumberOfPeople(stage.getNumberOfPeople() + 1);
                stage.setPeoplePic(stage.updatePeoplesPicArray());
                stage.updatePeoplesPicArray();
                stage.updatePeoplesArray();

                stage.setMaxSpeed(20);
                stage.setMinSpeed(10);
            }
            ArrayList<Integer> enemies = new ArrayList<>();
            for (Integer[] i : stage.getEnemiesPic()) {
                enemies.add(i[0]);
            }
            GuideFragment1 fragment = GuideFragment1.newInstance(enemies, lives, stage.getLevel());
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(android.R.id.content, fragment, "lal");
            transaction.addToBackStack(null);
            transaction.commit();

            do {
                assert fragment.getArguments() != null;
            } while ((boolean) fragment.getArguments().get("fragmentIsOpen"));

            resume();
        } else {
            stage.setTime(stage.getTime() + 1); //change to 5
            stage.setMinSpeed(stage.getMinSpeed() + 2);
            stage.setMaxSpeed(stage.getMaxSpeed() + 2);
            System.out.println("level is : " + stage.getLevel());
        }
        stage.setLevel(stage.getLevel() + 1);

    }

    private void firstStageGuide() {
    }

    private void update() {

        if (coronaStage) {
            covid.x -= covid.speed;
            if (covid.x + covid.width < 0) {
                if (!covid.firstTimeInit) {
                    isGameOver = true;
                }
                covid.x = screenX;
                covid.y = random.nextInt(screenY - covid.height);
                covid.firstTimeInit = false;
            }
        }

        for (People people : stage.getPeoples()) {
            people.updateObject(screenX, screenY, stage.getMinSpeed(), stage.getMaxSpeed());
        }
        for (Enemy enemy : stage.getEnemies()) {
            enemy.x -= enemy.speed;

            if (enemy.x + enemy.width < 0) {
                if (!enemy.firstTimeInit) {
                    if (!enemy.wasCovered) {
                        lives--;
                        if (lives == 0) {
                            isGameOver = true;
                            timer.cancel();
                            return;
                        }
                    }
                }
                enemy.speed = random.nextInt(stage.getMaxSpeed() + 1 - stage.getMinSpeed()) + stage.getMinSpeed();
                enemy.x = screenX;
                enemy.y = random.nextInt(screenY - enemy.height);
                enemy.wasCovered = false;
                enemy.firstTimeInit = false;
                enemy.Covered(false);
            }
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);

            pauseBtn = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
            pauseBtn = Bitmap.createScaledBitmap(pauseBtn, 100, 100, false);
            canvas.drawBitmap(pauseBtn, (screenX-screenX/8), screenY/8, paint);

            Bitmap redLive = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
            redLive = Bitmap.createScaledBitmap(redLive, 100 , 100, false);

            for (int i = 0; i < lives; i++) {
                canvas.drawBitmap(redLive, (100 * (i + 1)) * screenRatioX, 50 * screenRatioY, paint);
            }

            if (coronaStage) {
                canvas.drawBitmap(covid.getObject(), covid.getX(), covid.getY(), paint);
            }

            for (People people : stage.getPeoples()) {
                canvas.drawBitmap(people.getObject(), people.x, people.y, paint);
            }
            for (Enemy enemy : stage.getEnemies()) {
                canvas.drawBitmap(enemy.getObject(), enemy.x, enemy.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 164, paint);
            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                if (score > 0) saveIfHighScore();
                else {
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
        } else {
            int minValue = sp.getInt("1", 0);
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                if (minValue > (int) entry.getValue()) {
                    minValue = (int) entry.getValue();
                }
            }
            if (score > minValue) {
                openDialog();
            }
        }
    }

    private void openDialog() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
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
                        Intent intent = new Intent(activity, RecordActivity.class);
                        intent.putExtra("name", nameEt.getText().toString());
                        intent.putExtra("score", String.valueOf(score));
                        activity.startActivity(intent);
                    }
                });
            }
        });
    }

    private void openMenuDialog() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View dialogView = activity.getLayoutInflater().inflate(R.layout.menu_dialog, null);
                builder.setView(dialogView).setNegativeButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resume();
                    }
                }).setPositiveButton("menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }).setCancelable(false).show();

                ImageButton music_button = dialogView.findViewById(R.id.music_change);
                music_button.setSelected(!MusicPlayer.getInstance().getIsPaused());
                music_button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v.isSelected()){
                            v.setSelected(false);
                            MusicPlayer.getInstance().pause(true);
                        }else{
                            v.setSelected(true);
                            MusicPlayer.getInstance().play(true);
                        }
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (new RectF((screenX-screenX/8) * screenRatioX, 50 * screenRatioY,
                    (1600 + pauseBtn.getWidth()) * screenRatioX,
                    (100 + pauseBtn.getHeight()) * screenRatioY).contains(x, y)) {
                if (isPlaying) {
                    pause();
                    openMenuDialog();
                }
            }
            for (Enemy enemy : stage.getEnemies()) {
                if (enemy.getCollisionShape().contains(x, y)) {
                    if (!enemy.wasCovered && isPlaying) {
                        enemy.Covered(true);
                        score++;
                    }
                }
            }
            for (People people : stage.getPeoples()) {
                if (people.getCollisionShape().contains(x, y) && score > 0) {
                    score--;
                }
            }
            if (covid.getCollisionShape().contains(x, y)) {
                if (covid.width > 150) {
                    covid.decreaseSize(this.covid);
                } else {
                    score += 5;
                    covid = covid.increaseSize(this.covid, covidWidth, covidHeight);
                    coronaStage = false;
                }
            }
        }
        return true;
    }
}
