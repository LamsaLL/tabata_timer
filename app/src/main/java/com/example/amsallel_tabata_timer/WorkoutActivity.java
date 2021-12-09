package com.example.amsallel_tabata_timer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amsallel_tabata_timer.data.Counter;
import com.example.amsallel_tabata_timer.data.OnUpdateListener;
import com.example.amsallel_tabata_timer.db.Workout;

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

        Toast.makeText(WorkoutActivity.this, "Click : " + workout.getName(), Toast.LENGTH_SHORT).show();

        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);

        // Initialiser l'objet Compteur
        counter = new Counter();

        // Abonner l'activité au compteur pour "suivre" les événements
        counter.addOnUpdateListener(this);

        // Mise à jour graphique
        updating();
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
