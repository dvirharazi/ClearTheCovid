package com.example.dvir_game_try;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x = 0, y;
    Bitmap background;

    Background(int screenX, int screenY, Resources resources){
        background = BitmapFactory.decodeResource(resources, R.drawable.background_game);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }
}
