package com.example.amsallel_tabata_timer;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.amsallel_tabata_timer.db.DatabaseClient;
import com.example.amsallel_tabata_timer.db.Workout;

import java.util.ArrayList;
import java.util.List;
//Penser à l'asynchrone pour l'appel a la base de données
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

        // Add click event to listView
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the clicked workout thanks to adapter
                Workout workout = adapter.getItem(position);

                Intent WorkoutViewActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);

                WorkoutViewActivityIntent.putExtra("workout", workout);
                // Launch activity change demand
                startActivity(WorkoutViewActivityIntent);
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

                // Create new intent
                Intent WorkoutViewActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);

                WorkoutViewActivityIntent.putExtra("workout", (Parcelable) workout);
                // Launch activity change demand
                startActivity(WorkoutViewActivityIntent);

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

    public void onMore(View view) {
//        Log.i("lol", "lol");
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, view);

        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_actions, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(
                        MainActivity.this,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });

        popup.show(); //showing popup menu

    }
}