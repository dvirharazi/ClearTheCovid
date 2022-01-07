package com.example.dvir_game_try;

import static com.example.dvir_game_try.GameView.screenRatioX;
import static com.example.dvir_game_try.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.bumptech.glide.Glide;

public class Player {
    public boolean isGoingUp = false;
    public int toShoot = 0;
    int x, y, width, height, playerCounter = 0, shootCounter = 1;

    Player(GameView gameView, int screenY, Resources resources) {

        width = 890;
        height = 890;

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int)(height*screenRatioY);

        y = (int) ((screenY - 400) * screenRatioY);
        x = (int) (64 * screenRatioX);

    }



    Rect getCollisionShape(){
        return new Rect(x, y, (int) (x + width), (int) (y+ height));
    }
}
