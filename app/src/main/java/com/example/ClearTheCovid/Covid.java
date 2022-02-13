package com.example.ClearTheCovid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.io.Serializable;

public class Covid extends ObjectView implements Serializable {

    Integer[] picture;

    Covid(Resources resources, Integer [] picture, int minSpeed, int maxSpeed){
        super(resources, picture, minSpeed, maxSpeed);
        this.picture = picture;
        this.resources = resources;
    }

    public void decreaseSize(Covid covid){
        covid.object = Bitmap.createScaledBitmap(covid.getObject(), covid.width-=10,covid.height-=10, false);
    }
    public Covid increaseSize(float width, float height){
        Covid newCovid = new Covid(resources, new Integer[]{R.drawable.covid}, 10,20);
        newCovid.object = Bitmap.createScaledBitmap(newCovid.getObject(), (int)width, (int)height, false);
        return newCovid;
    }

    Rect getCollisionShape(){
        return new Rect(x, y,(x + width),(y+ height));
    }

}
