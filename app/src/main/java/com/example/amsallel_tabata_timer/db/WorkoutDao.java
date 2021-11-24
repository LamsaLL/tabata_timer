package com.example.amsallel_tabata_timer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workout")
    List<Workout> getAll();

    @Insert
    long insert(Workout workout);

    @Insert
    long[] insertAll(Wourkout... workouts);

    @Delete
    void delete(Workout workout);

    @Update
    void update(Workout workout);

}