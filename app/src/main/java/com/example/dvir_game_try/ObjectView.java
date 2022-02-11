package com.example.dvir_game_try;

//import static com.example.dvir_game_try.GameActivity.screenRatioX;
//import static com.example.dvir_game_try.GameActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class ObjectView {
    public int speed;
    public boolean firstTimeInit = true;
    int x=0, y, width, height;
    Bitmap object;
    Integer[] picture;
    Resources resources;
    Random random = new Random();



    ObjectView(Resources resources, Integer[] picture , int minSpeed, int maxSpeed){
        this.resources = resources;
        this.picture = picture;

        object = BitmapFactory.decodeResource(resources, picture[0]);

        width = object.getWidth();
        height = object.getHeight();

        width /=3;
        height/=3;

//        width = (int) (width * screenRatioX);
//        height = (int)(height * screenRatioY);

        object = Bitmap.createScaledBitmap(object, width, height, false);

        y =- height;

        speed = random.nextInt(maxSpeed + 1 - minSpeed) + minSpeed;
        System.out.println(speed);

    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getObject() {
        return object;
    }

    Rect getCollisionShape(){
        return new Rect(x, y, (int) (x + width), (int) (y+ height));
    }


    public void setPicture(int picture) {
        object = BitmapFactory.decodeResource(resources, picture);
        object = Bitmap.createScaledBitmap(object, width, height, false);
    }
}
