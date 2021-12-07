package com.example.amsallel_tabata_timer;


import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amsallel_tabata_timer.db.DatabaseClient;
import com.example.amsallel_tabata_timer.db.Workout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseClient mDb;
    private WorkoutsAdapter adapter;

    // VIEW
    private ListView workoutList;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get client database
        mDb = DatabaseClient.getInstance(getApplicationContext());

        //get views
        workoutList = findViewById(R.id.workoutList);
        buttonAdd = findViewById(R.id.button_add);

        // Link adapter to listView
        adapter = new WorkoutsAdapter(this, new ArrayList<Workout>());
        workoutList.setAdapter(adapter);

//        // Event on add button
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_ADD);
//            }
//        });

        // Add click event to listView
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the clicked workout thanks to adapter
                Workout workout = adapter.getItem(position);

                // Message
                Toast.makeText(MainActivity.this, "Click : " + workout.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add long click event to listView
        workoutList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the clicked workout thanks to adapter
                Workout workout = adapter.getItem(position);

                // Message
                Toast.makeText(MainActivity.this, "LongClick : " + workout.getName(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //getTasks();
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