package com.example.amsallel_tabata_timer;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amsallel_tabata_timer.data.Action;
import com.example.amsallel_tabata_timer.data.Counter;
import com.example.amsallel_tabata_timer.data.OnUpdateListener;
import com.example.amsallel_tabata_timer.db.Workout;

import java.util.ArrayList;
import java.util.Arrays;

public class WorkoutActivity extends AppCompatActivity implements OnUpdateListener{

    // VIEWS
    private TextView timerValue;
    private TextView actionLabelView;
    private RelativeLayout workoutLayoutView;
    private Button stopButtonView;
    private ToggleButton playPauseButtonView;
    private ImageButton stopImageButtonView;
    private ImageButton replayButtonView;
    // DATA
    private Counter counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Bundle extras = getIntent().getExtras();
        Workout workout = (Workout) extras.getSerializable("workout");

        ArrayList<Action> actions = new ArrayList<>();

        for(int i = 0; i < workout.getNumberOfSets() ; i++) {
            actions.add(new Action("PRÉPARER", workout.getPreparationTime(), 0x7DC71E));
            for (int y = 0; y < workout.getNumberOfCycles(); y++) {
                actions.add(new Action("TRAVAIL", workout.getWorkTime(), 0xAA0703));
                actions.add(new Action("REPOS", workout.getRestTime(), 0x1EB5C9));
            }
            actions.add(new Action("REPOS LONG", workout.getRestBtwSetsTime(), 0xDD9A2E));
        }

        workoutLayoutView = (RelativeLayout) findViewById(R.id.workoutLayoutView);
        actionLabelView = (TextView) findViewById(R.id.actionLabelView);

        timerValue = (TextView) findViewById(R.id.timerValue);
        stopButtonView = (Button) findViewById(R.id.stopButton);
        playPauseButtonView = (ToggleButton) findViewById(R.id.playPauseButton);
        stopImageButtonView = (ImageButton) findViewById(R.id.stopImageButton);
        replayButtonView = (ImageButton) findViewById(R.id.replayButton);

        //  We hide these buttons until we finish the workout
        stopImageButtonView.setVisibility(View.GONE);
        replayButtonView.setVisibility(View.GONE);

        // Initialiser l'objet Compteur
        counter = new Counter(actions.get(0).getTimeValue(), actions);
        actionLabelView.setText(actions.get(0).getLabel());

        // Abonner l'activité au compteur pour "suivre" les événements
        counter.addOnUpdateListener(this);

        playSound();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                counter.start();
            }
        }, 1500);

        // Mise à jour graphique
        updating();
    }

    public void playSound(){
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.beep1);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();
    }

    // Mise à jour graphique
    private void updating() {
        final Action currentAction = counter.getActions().get(0);
        final @ColorInt int actionColor = currentAction.getColor();
        final String formatedColor = String.format("#%06X", (0xFFFFFF & actionColor));
        // Affichage des informations du compteur
        timerValue.setText("" + String.format("%02d",counter.getMinutes()) + ":"
                + String.format("%02d", counter.getSecondes())
        );
        actionLabelView.setText(currentAction.getLabel());
        workoutLayoutView.setBackgroundColor(Color.parseColor(formatedColor));

        // If the last action timer is equal to 0 we display the finish view
        if(counter.getActions().size() == 1 && counter.getSecondes() == 0 && counter.getMillisecondes() == 0){
            actionLabelView.setText("");
            workoutLayoutView.setBackgroundColor(Color.parseColor("#2a912e"));
            timerValue.setText("FINI");
            stopImageButtonView.setVisibility(View.VISIBLE);
            replayButtonView.setVisibility(View.VISIBLE);
            stopButtonView.setVisibility(View.GONE);
            playPauseButtonView.setVisibility(View.GONE);
        }
    }


    /**
     * Méthode appelée à chaque update du compteur (l'activité est abonnée au compteur)
     *
     */
    @Override
    public void onUpdate() {
        updating();
    }

    public void onTogglePlayPause(View view){
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            counter.pause();
        } else {
            counter.start();
        }
    }

    // Replay same workout after finish it
    public void onReplay(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    // Get out of workout
    public void onClose(View view){
        counter.reset();
        WorkoutActivity.super.finish();
    }
}
