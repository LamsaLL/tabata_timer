package com.example.amsallel_tabata_timer;


import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.amsallel_tabata_timer.db.DatabaseClient;
import com.example.amsallel_tabata_timer.db.Workout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseClient mDb;
    private WorkoutsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = DatabaseClient.getInstance(getApplicationContext());

    }

    public void onAddWorkout(View view) {

        // Create new intent
        Intent AddWorkoutViewActivityIntent = new Intent(MainActivity.this, AddWorkoutActivity.class);

        // Launch activity change demand
        startActivity(AddWorkoutViewActivityIntent);
    }

    /**
     *
     *
     */
    private void getWorkouts() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetWorkouts extends AsyncTask<Void, Void, List<Workout>> {

            @Override
            protected List<Workout> doInBackground(Void... voids) {
                List<Workout> workoutList = mDb.getAppDatabase()
                        .workoutDao()
                        .getAll();
                return workoutList;
            }

            @Override
            protected void onPostExecute(List<Workout> workouts) {
                super.onPostExecute(workouts);

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(workouts);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetWorkouts gw = new GetWorkouts();
        gw.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Mise à jour des entrainements
        getWorkouts();
    }



}