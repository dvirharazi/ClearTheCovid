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
