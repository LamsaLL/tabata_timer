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
    int counterSec = 0;
    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    int counterMin = 0;

    class RptUpdater implements Runnable {
        final EditText editTextPrepSeconds = (EditText) findViewById(R.id.editTextPrepSeconds);

        public void run() {
            if( mAutoIncrement ){
                counterSec++;
                editTextPrepSeconds.setText(String.valueOf(counterSec));
                repeatUpdateHandler.postDelayed( new RptUpdater(), 50 );
            } else if( mAutoDecrement ){
                counterSec--;
                repeatUpdateHandler.postDelayed( new RptUpdater(), 50 );
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        final ImageButton upPrep = (ImageButton) findViewById(R.id.buttonUpPrep);

        final EditText editTextPrepSeconds = (EditText) findViewById(R.id.editTextPrepSeconds);

        upPrep.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post( new RptUpdater() );
                        return false;
                    }
                }
        );

        upPrep.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });

        editTextPrepSeconds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editTextPrepSeconds.getText().toString().equals("")) {
                    counterSec = 0;
                } else {
                    int newCounter = Integer.parseInt(editTextPrepSeconds.getText().toString());
                    counterSec = newCounter;
                }
            }
        });
    }



    public void onClose(View view) {
        AddWorkoutActivity.super.finish();
    }

    public void onTimeUp(View view) {
        final ImageButton upPrep = (ImageButton) findViewById(R.id.buttonUpPrep);
        final EditText editTextPrepSeconds = (EditText) findViewById(R.id.editTextPrepSeconds);

        counterSec++;
        editTextPrepSeconds.setText(String.valueOf(counterSec));
    }

    public void onTimeDown(View view) {
        final ImageButton upPrep = (ImageButton) findViewById(R.id.buttonDownPrep);

    }
}
