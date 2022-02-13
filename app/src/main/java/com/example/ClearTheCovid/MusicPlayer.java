package com.example.ClearTheCovid;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

    private static MusicPlayer instance = null;
    private MediaPlayer mediaPlayer;
    private boolean pausedByUser = false;


    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }
    //initialize the music
    public void initialize(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        play(false);
    }

    public void play(boolean byUser) {
        if(byUser) {//check if the music play by user or play because we move to the next page
            mediaPlayer.start();
            pausedByUser=false;
        }else if(!pausedByUser){
            mediaPlayer.start();
        }
    }

    public void pause(boolean byUser) {
        if(byUser)//check if the music pause by user or pause because we move to the next page
            pausedByUser = true;
        this.mediaPlayer.pause();
    }

    public boolean getIsPaused (){
        return pausedByUser;
    }
}
