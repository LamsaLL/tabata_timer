package com.example.amsallel_tabata_timer;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "workout")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    private String name;
    private int preparation_time;
    private int work_time;
    private int rest_time;
    private int rest_btw_sets_time;
    private int number_of_sets;
    private int number_of_cycles;

    /*
     * Getters and Setters
     * */
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public int getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getPreparationTime() {
        return preparation_time;
    }
    
    public void setPreparationTime(int preparation_time) {
        this.preparation_time = preparation_time;
    }
    
    public int getWorkTime() {
        return work_time;
    }
    
    public void setWorkTime(int work_time) {
        this.work_time = work_time;
    }
    
    public int getRestTime() {
        return rest_time;
    }
    
    public void setRestTime(int rest_time) {
        this.rest_time = rest_time;
    }    

    public int getRestBtwSetsTime() {
        return rest_btw_sets_time;
    }
    
    public void setRestBtwSetsTime(int rest_btw_sets_time) {
        this.rest_btw_sets_time = rest_btw_sets_time;
    }  
    
    public int getNumberOfSets() {
        return number_of_sets;
    }
    
    public void setNumberOfSets(int number_of_sets) {
        this.number_of_sets = number_of_sets;
    }    

    public int getNumberOfCycles() {
        return number_of_sets;
    }
    
    public void setNumberOfCyles(int number_of_cyles) {
        this.number_of_cycles = number_of_cyles;
    }    

}






