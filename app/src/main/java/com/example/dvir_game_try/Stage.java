package com.example.dvir_game_try;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Stage {
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
    List<Integer[]> characterRecycleList = new ArrayList<>();

    List<Integer[]> characterList = new ArrayList<>();

    public Stage(Resources resources, int number, int numberOfEnemies, int numberOfPeople, int time, int minSpeed, int maxSpeed) {

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


        this.resources = resources;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.level = number;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfPeople = numberOfPeople;
        this.enemiesPic = updateEnemiesPicArray();
        this.peoplePic = updatePeoplesPicArray();
        this.peoples = updatePeoplesArray();
        this.enemies = updateEnemiesArray();
        this.time = time;


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

    public void setPeoples(ArrayList<People> peoples) {
        this.peoples = peoples;
    }

    public ArrayList<Integer[]> getEnemiesPic() {
        return (ArrayList<Integer[]>) enemiesPic;
    }

    public void setEnemiesPic(ArrayList<Integer[]> enemiesPic) {
        this.enemiesPic = (ArrayList<Integer[]>) enemiesPic;
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
        return (ArrayList<Integer[]>) peoplePic;
    }

    public void setPeoplePic(ArrayList<Integer[]> peoplePic) {
        this.peoplePic = (ArrayList<Integer[]>) peoplePic;
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
}
