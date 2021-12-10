package com.example.amsallel_tabata_timer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        counter.start();
    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        counter.pause();
    }


    // Remettre à zéro le compteur
    public void onReset(View view) {
        counter.reset();
    }

//    public void startCounter(Action action){
//
//    }

    // Mise à jour graphique
    private void updating() {
        // Affichage des informations du compteur
        timerValue.setText("" + counter.getMinutes() + ":"
                + String.format("%02d", counter.getSecondes()) + ":"
                + String.format("%03d", counter.getMillisecondes()));
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
