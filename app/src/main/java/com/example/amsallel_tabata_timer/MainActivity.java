package com.example.amsallel_tabata_timer;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAddWorkout(View view) {

        // Create new intent
        Intent AddWorkoutViewActivityIntent = new Intent(MainActivity.this, AddWorkoutActivity.class);

        // Launch activity change demand
        startActivity(AddWorkoutViewActivityIntent);
    }



}