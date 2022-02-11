package com.example.dvir_game_try;

import static com.example.dvir_game_try.GameActivity.screenRatioX;
import static com.example.dvir_game_try.GameActivity.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x = 0, y;
    Bitmap background;

    Background(int screenX, int screenY, Resources resources){
        background = BitmapFactory.decodeResource(resources, R.drawable.bg1);
        background = Bitmap.createScaledBitmap(background, screenX, screenY , false);
    }
}
