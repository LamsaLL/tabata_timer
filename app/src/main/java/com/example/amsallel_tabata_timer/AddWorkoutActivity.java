package com.example.amsallel_tabata_timer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddWorkoutActivity extends AppCompatActivity {
    int counterSec = 0;
    int counterMin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
    }



    public void onClose(View view) {
        AddWorkoutActivity.super.finish();
    }

    public void onTimeUp(View view) {
        final ImageButton upPrep = (ImageButton) findViewById(R.id.buttonUpPrep);
        final EditText editTextPrepSeconds = (EditText) findViewById(R.id.editTextPrepSeconds);
        final EditText editTextPrepMinutes = (EditText) findViewById(R.id.edit_text_prep_minutes);

        if(counterSec < 60){
            counterSec++;
            editTextPrepSeconds.setText(String.valueOf(counterSec));
        }else{
            counterMin++;
            editTextPrepMinutes.setText(String.valueOf(counterMin));
        }

    }

    public void onTimeDown(View view) {
        final ImageButton upPrep = (ImageButton) findViewById(R.id.buttonDownPrep);

    }
}
