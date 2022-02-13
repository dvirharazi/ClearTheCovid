package com.example.dvir_game_try;

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
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    Timer timer = new Timer();
    Bitmap pauseBtn;
    private final Random random;
    private final GameActivity activity;
    private final Background background1;

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

    }

    @Override
    public void run() {
        timer = new Timer();
        TimerStage timerStage = new TimerStage(this.stage, this);
        timer.schedule(timerStage, 0, 1000);

        while (isPlaying) {
            update();
            draw();
            sleep();
        }
        timer.cancel();
    }

    private void update() {

        if (stage.isCoronaStage()) {
            stage.getCovid().x -= stage.getCovid().speed;
            if (stage.getCovid().x + stage.getCovid().width < 0) {
                if (!stage.getCovid().firstTimeInit) {
                    isGameOver = true;
                }
                stage.getCovid().x = screenX;
                stage.getCovid().y = random.nextInt(screenY - stage.getCovid().height);
                stage.getCovid().firstTimeInit = false;
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
                        stage.setLives(stage.getLives()-1);
                        if (stage.getLives() == 0) {
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
            canvas.drawBitmap(pauseBtn, (screenX - screenX / 8), 50, paint);

            Bitmap redLive = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
            redLive = Bitmap.createScaledBitmap(redLive, 100, 100, false);

            for (int i = 0; i < stage.getLives(); i++) {
                canvas.drawBitmap(redLive, (100 * (i + 1)), 50, paint);
            }

            if (stage.isCoronaStage()) {
                canvas.drawBitmap(stage.getCovid().getObject(), stage.getCovid().getX(), stage.getCovid().getY(), paint);
            }

            for (People people : stage.getPeoples()) {
                canvas.drawBitmap(people.getObject(), people.x, people.y, paint);
            }
            for (Enemy enemy : stage.getEnemies()) {
                canvas.drawBitmap(enemy.getObject(), enemy.x, enemy.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 150, paint);


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
        if (sp.getAll().size() < 10) {
            openDialog();
        } else {
            int minValue = Integer.MAX_VALUE;
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                String [] recordInfo = entry.getValue().toString().split("\\s");
                if (minValue > Integer.parseInt(recordInfo[0])) {
                    minValue = Integer.parseInt(recordInfo[0]);
                }
            }
            if (score > minValue) {
                openDialog();
            }else{
                waitBeforeExiting();
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

                        if (nameEt.getText().toString().length() == 0) { //Name validation
                            nameEt.setError(getResources().getString(R.string.please_enter_your_name));
                        }
                        if(nameEt.getText().toString().length() != 0) {

                            activity.startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void openMenuDialog() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                View viewInflater = inflater.inflate(R.layout.menu_dialog, null);
                builder.setView(viewInflater);
                AlertDialog finishDialog = builder.create();
                finishDialog.setCancelable(false);
                finishDialog.show();

                ImageButton music_button = viewInflater.findViewById(R.id.music_change);
                music_button.setSelected(!MusicPlayer.getInstance().getIsPaused());
                music_button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.isSelected()) {
                            v.setSelected(false);
                            MusicPlayer.getInstance().pause(true);
                        } else {
                            v.setSelected(true);
                            MusicPlayer.getInstance().play(true);
                        }
                    }
                });
                ImageButton home_btn = viewInflater.findViewById(R.id.home_btn);
                home_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishDialog.dismiss();
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
                ImageButton back_to_game_btn = viewInflater.findViewById(R.id.exit_dialog);
                back_to_game_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishDialog.dismiss();
                        resume();
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
            if (new RectF((screenX - screenX / 8), 50,
                    (screenX - screenX / 8) + pauseBtn.getWidth(),
                    50 + pauseBtn.getHeight()).contains(x, y)) {
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
            if (stage.getCovid().getCollisionShape().contains(x, y)) {
                if (stage.getCovid().width > 150) {
                    stage.getCovid().decreaseSize(stage.getCovid());
                } else {
                    score += 5;
                    stage.setCovid(stage.getCovid().increaseSize(stage.getCovid(), stage.covidWidth, stage.covidHeight));
                    stage.setCoronaStage(false);
                }
            }
        }
        return true;
    }
}
