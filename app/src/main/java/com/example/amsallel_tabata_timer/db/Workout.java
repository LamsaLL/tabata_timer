package com.example.amsallel_tabata_timer.db;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "workout")
public class Workout implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private String name;
    private int preparationTime;
    private int workTime;
    private int restTime;
    private int restBtwSetsTime;
    private int numberOfSets;
    private int numberOfCycles;

    /*
     * Getters and Setters
     * */
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }


    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getRestBtwSetsTime() {
        return restBtwSetsTime;
    }

    public void setRestBtwSetsTime(int restBtwSetsTime) {
        this.restBtwSetsTime = restBtwSetsTime;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public int getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(int numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }
}






