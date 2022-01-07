package com.example.dvir_game_try;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
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
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    private Paint paint;
    private Player player;
    int updateLives;
    private SharedPreferences sp;
    //    private List<Injection> injections;
//    private Covid[] covids;
    private ArrayList<Covid> covids = new ArrayList<>();
    private ArrayList<People> peoples = new ArrayList<>();

    private int lives = 3;
    //    private People[] peoples;
    private Random random;
    private GameActivity activity;
    public static float screenRatioX, screenRatioY;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;
        sp = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

//        injections = new ArrayList<>();

        player = new Player(this, screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);


        for (int i = 0; i < 5; i++) {
            covids.add(new Covid(getResources(), R.drawable.covid));
        }
        for (int i = 0; i < 5; i++) {
            peoples.add(new People(getResources(), R.drawable.unmasked));
        }

        random = new Random();
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

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) { //all the image outside the screen
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) { //all the image outside the screen
            background2.x = screenX;
        }
        if (player.isGoingUp) {
            player.y -= 200 * screenRatioY;
            player.isGoingUp = false;
        }
        if (player.y < 0) {
            player.y = 0;
        }

        for (int i = 0; i < covids.size(); i++) {
            covids.get(i).x -= covids.get(i).speed;

            if (covids.get(i).x + covids.get(i).width < 0) {

                int bound = (int) (30 * screenRatioX);
                covids.get(i).speed = random.nextInt(bound);

                if (covids.get(i).speed < 10 * screenRatioX) {
                    covids.get(i).speed = (int) (10 * screenRatioX);
                }
                covids.get(i).x = screenX;
                covids.get(i).y = random.nextInt(screenY - covids.get(i).height);
            }

            updateLives = covids.get(i).isInfected(player, lives, score);
            if (lives != updateLives) {
                covids.remove(covids.get(i));
                covids.add(new Covid(getResources(), R.drawable.covid));
                lives = updateLives;
                if (lives == 0) {
                    isGameOver = true;

                }
                return;
            }
//            if (Rect.intersects(covids.get(i).getCollisionShape(), player.getCollisionShape())) {
//
//            }
        }

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
            updateLives = people.isInfected(player, lives, score);
            if (lives != updateLives) {
                peoples.remove(people);
                peoples.add(new People(getResources(), R.drawable.masked));
                lives = updateLives;
                if (lives == 0) {
                    isGameOver = true;

                }
                return;


//            if (Rect.intersects(people.getCollisionShape(), player.getCollisionShape()) && !people.wasCovered) {
//                isGameOver = true;
//                return;
//            }
            }
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Covid covid : covids) {
                canvas.drawBitmap(covid.getObject(), covid.x, covid.y, paint);
            }
            for (People people : peoples) {
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
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }
            Glide.with(this.activity).asGif().load(R.drawable.running_right).into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                    resource.setBounds(player.getCollisionShape());
                    resource.draw(canvas);
                    resource.start();
                }
            });

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (sp.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                if(event.getX() < screenX /2){
//                    player.isGoingUp = true;
//                }
//                if(event.getX() > screenX /2){
//                    player.toShoot++;
//                }
//                break;
//
//            case MotionEvent.ACTION_UP:
//                if(event.getX() < screenX /2) {
//                    player.y += 200 * screenRatioY;
//                }
//                break;
//        }
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for (People people : peoples) {
                    if (people.getCollisionShape().contains(x, y)) {
                        people.Covered(true);
                        score++;
                    }
                }
        }
        return true;
    }
}
