package com.example.dvir_game_try;

import static com.example.dvir_game_try.GameView.screenRatioX;
import static com.example.dvir_game_try.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;

public class ObjectView {
    public int speed=20;
    int x=0, y, width, height;
    Bitmap object;
    int picture;
    Resources resources;
    Random random = new Random();

    ObjectView(Resources resources, int picture){
        this.resources = resources;
        this.picture = picture;
        object = BitmapFactory.decodeResource(resources, picture);

        width = object.getWidth();
        height = object.getHeight();

        width /=3;
        height/=3;

        width = (int) (width * screenRatioX);
        height = (int)(height*screenRatioY);

        object = Bitmap.createScaledBitmap(object, width, height, false);

        y =- height;
    }
//

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

//    public void updateObject(int screenX, int screenY){
//        this.x -= this.speed;
//
//        if(this.x + this.width < 0) {
//
//            int bound = (int) (30 * screenRatioX);
//            this.speed = random.nextInt(30);
//
//            if (this.speed < 10 * screenRatioX) {
//                this.speed = (int) (10 * screenRatioX);
//            }
//            this.x = screenX;
//            this.y = random.nextInt(screenY - this.height);
//        }
//    }
    public void setPicture(int picture) {
        this.picture = picture;
        object = BitmapFactory.decodeResource(resources, picture);
        object = Bitmap.createScaledBitmap(object, width, height, false);
    }

    public int isInfected(Player player, int lives, int score){
        if(Rect.intersects(this.getCollisionShape(), player.getCollisionShape())) {
            return 1;
        }
        return 0;
    }
}
