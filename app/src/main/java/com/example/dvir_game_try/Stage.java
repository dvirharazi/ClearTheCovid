package com.example.dvir_game_try;

import android.content.res.Resources;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Fragments.GuideFragment_2;
import Fragments.InfoFragment;

public class Stage {
    public final float covidWidth;
    public final float covidHeight;
    private int level;
    private int numberOfEnemies;
    private int numberOfPeople;
    ArrayList<People> peoples = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Integer[]> enemiesPic = new ArrayList<>();
    ArrayList<Integer[]> peoplePic = new ArrayList<>();
    private int minSpeed, maxSpeed;
    Resources resources;
    int time;
    int specificObject;
    Random random = new Random();
    private int screenX;
    private int screenY;
    private int lives;
    private final GameActivity activity;



    private static boolean coronaStage = false;


    private Covid covid;
    static List<Integer[]> characterRecycleList = new ArrayList<>();

    static List<Integer[]> characterList = new ArrayList<>();



    public Stage(GameActivity activity, Resources resources, int number, int numberOfEnemies, int numberOfPeople, int time, int minSpeed, int maxSpeed, int screenX, int screenY) {

        characterList.add(new Integer[]{R.drawable.people_1, R.drawable.m_people_1});
        characterList.add(new Integer[]{R.drawable.people_2, R.drawable.m_people_2});
        characterList.add(new Integer[]{R.drawable.people_3, R.drawable.m_people_3});
        characterList.add(new Integer[]{R.drawable.people_4, R.drawable.m_people_4});
        characterList.add(new Integer[]{R.drawable.people_5, R.drawable.m_people_5});
        characterList.add(new Integer[]{R.drawable.people_6, R.drawable.m_people_6});
        characterList.add(new Integer[]{R.drawable.people_7, R.drawable.m_people_7});
        characterList.add(new Integer[]{R.drawable.people_8, R.drawable.m_people_8});
        characterList.add(new Integer[]{R.drawable.people_9, R.drawable.m_people_9});
        characterList.add(new Integer[]{R.drawable.people_10, R.drawable.m_people_10});
        characterList.add(new Integer[]{R.drawable.people_11, R.drawable.m_people_11});
        characterList.add(new Integer[]{R.drawable.people_12, R.drawable.m_people_12});
        characterList.add(new Integer[]{R.drawable.people_13, R.drawable.m_people_13});
        characterList.add(new Integer[]{R.drawable.people_14, R.drawable.m_people_14});
        characterList.add(new Integer[]{R.drawable.people_15, R.drawable.m_people_15});
        characterList.add(new Integer[]{R.drawable.people_16, R.drawable.m_people_16});
        characterList.add(new Integer[]{R.drawable.people_18, R.drawable.m_people_18});
        characterList.add(new Integer[]{R.drawable.people_19, R.drawable.m_people_19});
        characterList.add(new Integer[]{R.drawable.people_20, R.drawable.m_people_20});
        characterList.add(new Integer[]{R.drawable.people_21, R.drawable.m_people_21});

        this.activity = activity;
        covid = new Covid(resources, new Integer[]{R.drawable.covid}, 10, 20);

        this.covidWidth = covid.getWidth();
        this.covidHeight = covid.getHeight();

        this.screenX = screenX;
        this.screenY = screenY;
        this.lives = 3;

        this.resources = resources;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.level = number;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfPeople = numberOfPeople;
        this.enemiesPic = updateEnemiesPicArray();
        this.peoplePic = updatePeoplesPicArray();
        this.enemies = updateEnemiesArray();
        this.peoples = updatePeoplesArray();
        this.time = time;


    }

    public void setCovid(Covid covid) {
        this.covid = covid;
    }
    public int getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public void setNumberOfEnemies(int numberOfEnemies) {
        this.numberOfEnemies = numberOfEnemies;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public ArrayList<People> getPeoples() {
        return peoples;
    }

    public ArrayList<Integer[]> getEnemiesPic() {
        return enemiesPic;
    }

    public void setEnemiesPic(ArrayList<Integer[]> enemiesPic) {
        this.enemiesPic = enemiesPic;
    }
    public Covid getCovid() {
        return covid;
    }

    public static boolean isCoronaStage() {
        return coronaStage;
    }

    public static void setCoronaStage(boolean coronaStage) {
        Stage.coronaStage = coronaStage;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Integer[]> getPeoplePic() {
        return peoplePic;
    }

    public void setPeoplePic(ArrayList<Integer[]> peoplePic) {
        this.peoplePic = peoplePic;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


    public ArrayList<Integer[]> updateEnemiesPicArray() {
        enemiesPic.clear();
        for (int i = 0; i < this.getNumberOfEnemies(); i++) {
            specificObject = random.nextInt(characterList.size());
            enemiesPic.add(characterList.get(specificObject));
            characterRecycleList.add(characterList.get(specificObject));
            characterList.remove(characterList.get(specificObject));
        }
        return enemiesPic;
    }

    public ArrayList<Integer[]> updatePeoplesPicArray() {
        peoplePic.clear();
        for (int i = 0; i < this.getNumberOfPeople(); i++) {
            specificObject = random.nextInt(characterList.size());
            peoplePic.add(characterList.get(specificObject));
            characterRecycleList.add(characterList.get(specificObject));
            characterList.remove(characterList.get(specificObject));
        }
        characterList.addAll(characterRecycleList);
        characterRecycleList.clear();
        return peoplePic;
    }

    public ArrayList<Enemy> updateEnemiesArray() {
        enemies.clear();
        for (int i = 0; i < this.getEnemiesPic().size(); i++) {
            enemies.add(new Enemy(resources, enemiesPic.get(i), minSpeed, maxSpeed));
        }
        return enemies;
    }

    public ArrayList<People> updatePeoplesArray() {
        peoples.clear();
        for (int i = 0; i < this.getPeoplePic().size(); i++) {
            peoples.add(new People(resources, peoplePic.get(i), minSpeed, maxSpeed));
        }
        return peoples;
    }
    public void upgradeDifficult() {
        this.setMinSpeed(this.getMinSpeed() + 1);
        this.setMaxSpeed(this.getMaxSpeed() + 1);

        if(this.getMinSpeed()% 5 == 0){
            coronaStage = true;
        }
    }

    public void resetDifficult() {
        this.setMinSpeed(10);
        this.setMaxSpeed(20);
        covid.x = screenX;
        covid.y = random.nextInt(screenY - covid.height);
    }

    public void goToNextStage() {

        this.setNumberOfEnemies(this.getNumberOfEnemies() + 1);
        this.setEnemiesPic(this.updateEnemiesPicArray());
        this.updateEnemiesPicArray();
        this.updateEnemiesArray();

        this.setNumberOfPeople(this.getNumberOfPeople() + 1);
        this.setPeoplePic(this.updatePeoplesPicArray());
        this.updatePeoplesPicArray();
        this.updatePeoplesArray();

        resetDifficult();
        stageGuide();
        this.setTime(this.getTime() + 3);
        this.setLevel(this.getLevel() + 1);
    }
    public void stageGuide() {
        ArrayList<Integer> enemies = new ArrayList<>();
        for (Integer[] i : this.getEnemiesPic()) {
            enemies.add(i[0]);
        }
        InfoFragment fragment = InfoFragment.newInstance(enemies, this.lives, this.getLevel());
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(android.R.id.content, fragment, "lal");
        transaction.addToBackStack("info");
        transaction.commit();


        do {
            assert fragment.getArguments() != null;
        } while ((boolean) fragment.getArguments().get("fragmentIsOpen"));
    }
}
