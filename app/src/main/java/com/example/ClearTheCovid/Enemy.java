package com.example.ClearTheCovid;

import android.content.res.Resources;
import android.graphics.Rect;

import java.io.Serializable;

public class Enemy extends ObjectView implements Serializable {
    public boolean wasCovered = false;
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
        return new Rect(x, y, (x + width), (y+ height));
    }

}

