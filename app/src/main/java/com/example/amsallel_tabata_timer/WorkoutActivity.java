package com.example.amsallel_tabata_timer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amsallel_tabata_timer.data.Action;
import com.example.amsallel_tabata_timer.data.Counter;
import com.example.amsallel_tabata_timer.data.OnUpdateListener;
import com.example.amsallel_tabata_timer.db.Workout;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity implements OnUpdateListener{
    // VIEW
    private TextView timerValue;
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
            actions.add(new Action("P", workout.getPreparationTime()));
            for (int y = 0; y < workout.getNumberOfCycles(); y++) {
                actions.add(new Action("W", workout.getWorkTime()));
                actions.add(new Action("R", workout.getRestTime()));
            }
            actions.add(new Action("RBS", workout.getRestBtwSetsTime()));
        }

        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);

        // Initialiser l'objet Compteur
        counter = new Counter(actions.remove(0).getTimeValue(), actions);

        // Abonner l'activité au compteur pour "suivre" les événements
        counter.addOnUpdateListener(this);
        // Mise à jour graphique
        updating();

//        startCounter(actions.remove(0));
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
            counter.start();
        } else {
            counter.pause();
        }
    }


    // Remettre à zéro le compteur
    public void onReset(View view) {
        counter.reset();
    }

    // Sortir de l'entrainement
    public void onClose(View view){
        WorkoutActivity.super.finish();
    }

//    public void startCounter(Action action){
//
//    }

    // Mise à jour graphique
    private void updating() {
        // Affichage des informations du compteur
        timerValue.setText("" + String.format("%02d",counter.getMinutes()) + ":"
                + String.format("%02d", counter.getSecondes())
        );
    }

    /**
     * Méthode appelée à chaque update du compteur (l'activité est abonnée au compteur)
     *
     */
    @Override
    public void onUpdate() {
        updating();
    }

}
