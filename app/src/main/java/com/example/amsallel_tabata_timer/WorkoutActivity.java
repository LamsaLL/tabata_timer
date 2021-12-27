package com.example.amsallel_tabata_timer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

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
            actions.add(new Action("Prepare", workout.getPreparationTime()));
            for (int y = 0; y < workout.getNumberOfCycles(); y++) {
                actions.add(new Action("Work", workout.getWorkTime()));
                actions.add(new Action("Rest", workout.getRestTime()));
            }
            actions.add(new Action("RBS", workout.getRestBtwSetsTime()));
        }

        actionLabelView = (TextView) findViewById(R.id.actionLabelView);

        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);
        // Initialiser l'objet Compteur
        counter = new Counter(actions.get(0).getTimeValue(), actions);
        actionLabelView.setText(actions.get(0).getLabel());

        // Abonner l'activité au compteur pour "suivre" les événements
        counter.addOnUpdateListener(this);
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
        actionLabelView.setText(counter.getActions().get(0).getLabel());
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
