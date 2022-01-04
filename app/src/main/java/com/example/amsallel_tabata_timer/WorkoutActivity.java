package com.example.amsallel_tabata_timer;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

    // VIEW
    private TextView timerValue;
    private TextView actionLabelView;
    private RelativeLayout workoutLayoutView;
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
        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);
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

    // Lancer le compteur
    public void onStart(View view) {
        //mettre timer == null ou disable le bouton play pour éviter bug
        counter.start();
    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        counter.pause();
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


    // Remettre à zéro le compteur
    public void onReset(View view) {
        counter.reset();
    }

    // Sortir de l'entrainement
    public void onClose(View view){
        counter.reset();
        WorkoutActivity.super.finish();
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

        //if the last action timer is equal to 0 we display the finish view
        if(counter.getActions().size() == 1 && counter.getSecondes() == 0 && counter.getMillisecondes() == 0){
            Log.i("finish!", "finish!");
            actionLabelView.setText("");
            workoutLayoutView.setBackgroundColor(Color.parseColor("#055555"));
            timerValue.setText("Fini!");

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

    public void playSound(){
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.beep1);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();
    }
}
