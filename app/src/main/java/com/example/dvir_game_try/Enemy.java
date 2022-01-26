package com.example.dvir_game_try;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.io.Serializable;

public class Enemy extends ObjectView implements Serializable {
    public boolean wasCovered = false;
    Bitmap unmaskedPeople, maskedPeople;
    int picIndex;
    Integer[] picture;

    Enemy(Resources resources, Integer[] picture, int minSpeed, int maxSpeed){
        super(resources, picture,minSpeed, maxSpeed);
        this.picture = picture;
        this.resources = resources;
    }


    public void Covered(boolean isCovered){
        if(isCovered){
            this.wasCovered = true;
            setPicture(picture[1]);
        }
        else setPicture(this.picture[0]);
    }
    Rect getCollisionShape(){
        return new Rect(x, y, (int) (x + width), (int) (y+ height));
    }

//    @Override
//    public int isInfected(Player player, int lives, int score) {
//        int isInfected = super.isInfected(player, lives, score);
//        if(isInfected == 1 && !this.wasCovered){
//            lives--;
//        }
//        return lives;
//    }

//    public int isInfected(People people, int lives, int score) {
////        int isInfected = super.isInfected(people, lives, score);
//        if((people.getX() + people.getWidth()) < 0 && !this.wasCovered){
//            lives--;
//        }
//        return lives;
//    }


    public Integer[] getPicture() {
        return picture;
    }
}

