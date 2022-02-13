package com.example.dvir_game_try;

import java.util.TimerTask;

public class TimerStage extends TimerTask {

    private static int stageTimerCount;
    private Stage stage;
    private GameView gameView;

    public TimerStage(Stage stage, GameView gameView) {
        this.stage = stage;
        stageTimerCount = stage.getTime();
        this.gameView = gameView;
    }

    public static int getStageTimerCount() {
        return stageTimerCount;
    }

    public static void setStageTimerCount(int stageTimerCount) {
        TimerStage.stageTimerCount = stageTimerCount;
    }

    @Override
    public void run() {
        if(stageTimerCount < 0){
            gameView.pause();
            stage.goToNextStage();
            gameView.resume();
            stageTimerCount = stage.getTime();
        }
        System.out.println("i: " + stageTimerCount);
        if (stage.getLevel() == 1) {
            gameView.pause();
            stage.stageGuide();
            gameView.resume();
            stage.setLevel(stage.getLevel() + 1);
        }
        if (stageTimerCount >= 0) {
            stageTimerCount--;
            if (stageTimerCount % 2 == 0) {
                stage.upgradeDifficult();
            }
        }
    }
}
