package com.example.dvir_game_try;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Covid extends ObjectView {

    Bitmap covid;

    Covid(Resources resources, Integer[] picture , int minSpeed, int maxSpeed){
        super(resources, picture, minSpeed , maxSpeed);
    }


    Rect getCollisionShape(){
        return new Rect(x, y, (int) (x + width), (int) (y+ height));
    }
//    @Override
//    public int isInfected(Player player, int lives, int score) {
//        int isInfected = super.isInfected(player, lives, score);
//        if (isInfected == 1) {
//            lives--;
//        }
//        return lives;
//    }
}
