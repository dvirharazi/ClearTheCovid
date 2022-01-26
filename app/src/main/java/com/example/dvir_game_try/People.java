package com.example.dvir_game_try;

import android.content.res.Resources;

import java.io.Serializable;


public class People extends ObjectView implements Serializable {

    Integer[] picture;



    People(Resources resources, Integer[] picture, int minSpeed, int maxSpeed){
        super(resources, picture, minSpeed, maxSpeed);
        this.picture = picture;
        this.resources = resources;
    }

//    public void Covered(boolean isCovered){
//        if(isCovered){
//            this.wasCovered = true;
//            setPicture(R.drawable.m_people_1);
//        }
//        else setPicture(this.picture);
//    }
//    Rect getCollisionShape(){
//        return new Rect(x, y, (int) (x + width), (int) (y+ height));
//    }

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

    public void updateObject(int screenX, int screenY, int minSpeed, int maxSpeed){
        this.x -= this.speed;
        if(this.x + this.width < 0) {
            this.speed = random.nextInt(maxSpeed + 1 - minSpeed) + minSpeed;
            this.x = screenX;
            this.y = random.nextInt(screenY - this.height);
        }
    }

    public Integer[] getPicture() {
        return picture;
    }
}
