package com.example.amsallel_tabata_timer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AddWorkoutActivity extends AppCompatActivity {
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

        final EditText editTextPrepSeconds = (EditText) findViewById(R.id.editTextPrepSeconds);
        final EditText editTextWorkSeconds = (EditText) findViewById(R.id.editTextWorkSeconds);
        final EditText editTexRestSeconds = (EditText) findViewById(R.id.editTexRestSeconds);
        final EditText editTextCycles = (EditText) findViewById(R.id.editTextCycles);
        final EditText editTextSets = (EditText) findViewById(R.id.editTextSets);
        final EditText editTextRestBtwSetsSeconds = (EditText) findViewById(R.id.editTextRestBtwSetsSeconds);

        handleEditTimers(editTextPrepSeconds, buttonUpPrep, buttonDownPrep);
        handleEditTimers(editTextWorkSeconds, buttonUpWork, buttonDownWork);
        handleEditTimers(editTexRestSeconds, buttonUpRest, buttonDownRest);
        handleEditTimers(editTextCycles, buttonUpCycles, buttonDownCycles);
        handleEditTimers(editTextSets, buttonUpSets, buttonDownSets);
        handleEditTimers(editTextRestBtwSetsSeconds, buttonUpRestBtwSets, buttonDownRestBtwSets);
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

    public void onClose(View view) {
        AddWorkoutActivity.super.finish();
    }
}
