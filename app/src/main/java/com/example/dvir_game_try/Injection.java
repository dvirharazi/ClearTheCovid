package com.example.dvir_game_try;

import static com.example.dvir_game_try.GameView.screenRatioX;
import static com.example.dvir_game_try.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Injection {

    int x,y, width, height;
    Bitmap injection;

    Injection(Resources resources){
//        injection = BitmapFactory.decodeResource(resources, R.drawable.injection);
        width = injection.getWidth();
        height = injection.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int)(height*screenRatioY);

        injection = Bitmap.createScaledBitmap(injection, width,height,false);

    }

    Rect getCollisionShape(){
        return new Rect(x, y, x + width, y+ height);
    }
}
