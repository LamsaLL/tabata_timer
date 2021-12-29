package com.example.amsallel_tabata_timer;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amsallel_tabata_timer.db.DatabaseClient;
import com.example.amsallel_tabata_timer.db.Workout;

import java.util.List;

public class AddWorkoutActivity extends AppCompatActivity {

    //VIEWS
    private EditText editTextNameWorkoutView;
    private EditText editTextPrepSecondsView;
    private EditText editTextWorkSecondsView;
    private EditText editTexRestSecondsView;
    private EditText editTextCyclesView;
    private EditText editTextSetsView;
    private EditText editTextRestBtwSetsSecondsView;

    //DB
    private DatabaseClient mDb;

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;

    class RptUpdater implements Runnable {
        private EditText editTextView;

        public RptUpdater(EditText editTextView) {
            this.editTextView = editTextView;
        }

        public void run() {
            if (mAutoIncrement) {
                int counter = Integer.parseInt(String.valueOf(editTextView.getText()));
                counter++;
                editTextView.setText(String.valueOf(counter));
                repeatUpdateHandler.postDelayed(new RptUpdater(editTextView), 50);
            } else if (mAutoDecrement) {
                int counter = Integer.parseInt(String.valueOf(editTextView.getText()));
                if (counter > 0) {
                    counter--;
                }
                editTextView.setText(String.valueOf(counter));
                repeatUpdateHandler.postDelayed(new RptUpdater(editTextView), 50);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        // get client database
        mDb = DatabaseClient.getInstance(getApplicationContext());

        final ImageButton buttonUpPrep = (ImageButton) findViewById(R.id.buttonUpPrep);
        final ImageButton buttonDownPrep = (ImageButton) findViewById(R.id.buttonDownPrep);
        final ImageButton buttonUpWork = (ImageButton) findViewById(R.id.buttonUpWork);
        final ImageButton buttonDownWork = (ImageButton) findViewById(R.id.buttonDownWork);
        final ImageButton buttonUpRest = (ImageButton) findViewById(R.id.buttonUpRest);
        final ImageButton buttonDownRest = (ImageButton) findViewById(R.id.buttonDownRest);
        final ImageButton buttonUpCycles = (ImageButton) findViewById(R.id.buttonUpCycles);
        final ImageButton buttonDownCycles = (ImageButton) findViewById(R.id.buttonDownCycles);
        final ImageButton buttonUpSets = (ImageButton) findViewById(R.id.buttonUpSets);
        final ImageButton buttonDownSets = (ImageButton) findViewById(R.id.buttonDownSets);
        final ImageButton buttonUpRestBtwSets = (ImageButton) findViewById(R.id.buttonUpRestBtwSets);
        final ImageButton buttonDownRestBtwSets = (ImageButton) findViewById(R.id.buttonDownRestBtwSets);

        editTextNameWorkoutView = (EditText) findViewById(R.id.nameWorkout);
        editTextPrepSecondsView = (EditText) findViewById(R.id.editTextPrepSeconds);
        editTextWorkSecondsView = (EditText) findViewById(R.id.editTextWorkSeconds);
        editTexRestSecondsView = (EditText) findViewById(R.id.editTexRestSeconds);
        editTextCyclesView = (EditText) findViewById(R.id.editTextCycles);
        editTextSetsView = (EditText) findViewById(R.id.editTextSets);
        editTextRestBtwSetsSecondsView = (EditText) findViewById(R.id.editTextRestBtwSetsSeconds);

        handleEditTimers(editTextPrepSecondsView, buttonUpPrep, buttonDownPrep);
        handleEditTimers(editTextWorkSecondsView, buttonUpWork, buttonDownWork);
        handleEditTimers(editTexRestSecondsView, buttonUpRest, buttonDownRest);
        handleEditTimers(editTextCyclesView, buttonUpCycles, buttonDownCycles);
        handleEditTimers(editTextSetsView, buttonUpSets, buttonDownSets);
        handleEditTimers(editTextRestBtwSetsSecondsView, buttonUpRestBtwSets, buttonDownRestBtwSets);
    }

    public void handleEditTimers(EditText editTextView, ImageButton buttonUp, ImageButton buttonDown) {
        buttonUp.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post(new RptUpdater(editTextView));
                        return false;
                    }
                }
        );

        buttonDown.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post(new RptUpdater(editTextView));
                        return false;
                    }
                }
        );

        buttonUp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement) {
                    mAutoIncrement = false;
                }
                return false;
            }
        });

        buttonDown.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement) {
                    mAutoDecrement = false;
                }
                return false;
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int counter = Integer.parseInt(String.valueOf(editTextView.getText()));
                counter++;
                editTextView.setText(String.valueOf(counter));
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int counter = Integer.parseInt(String.valueOf(editTextView.getText()));
                if (counter > 0) {
                    counter--;
                }
                editTextView.setText(String.valueOf(counter));
            }
        });
    }

    public void saveWorkout() {
        //Get values in views
        final String name = editTextNameWorkoutView.getText().toString().trim();
        final int preparationTime = Integer.parseInt(editTextPrepSecondsView.getText().toString().trim());
        final int workTime = Integer.parseInt(editTextWorkSecondsView.getText().toString().trim());
        final int restTime = Integer.parseInt(editTexRestSecondsView.getText().toString().trim());
        final int numberOfCycles = Integer.parseInt(editTextCyclesView.getText().toString().trim());
        final int numberOfSets = Integer.parseInt(editTextSetsView.getText().toString().trim());
        final int restBtwSetsTime = Integer.parseInt(editTextRestBtwSetsSecondsView.getText().toString().trim());

        class SaveWorkout extends AsyncTask<Void, Void, Workout>{

            @Override
            protected Workout doInBackground(Void... voids) {
                //Create the workout
                Workout workout = new Workout();
                workout.setName(name);
                workout.setPreparationTime(preparationTime);
                workout.setWorkTime(workTime);
                workout.setRestTime(restTime);
                workout.setNumberOfCycles(numberOfCycles);
                workout.setNumberOfSets(numberOfSets);
                workout.setRestBtwSetsTime(restBtwSetsTime);

                mDb.getAppDatabase()
                        .workoutDao()
                        .insert(workout);
                return workout;
            }

            @Override
            protected void onPostExecute(Workout workout) {
                super.onPostExecute(workout);

                //We stop the activity when workout has been created
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }
        SaveWorkout saveWorkout  = new SaveWorkout();
        saveWorkout.execute();
    }

    public void onClose(View view) {
        AddWorkoutActivity.super.finish();
    }

    public void onSave (View view )  {
        saveWorkout();
    }
}
