package com.example.amsallel_tabata_timer;


import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

    // VIEWS
    private ListView workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get client database
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Get views
        workoutList = findViewById(R.id.workoutList);

        // Link adapter to listView
        adapter = new WorkoutsAdapter(this, new ArrayList<Workout>());
        workoutList.setAdapter(adapter);
    }

    public void onAddWorkout(View view) {
        // Create new intent
        Intent AddWorkoutViewIntent = new Intent(MainActivity.this, SaveWorkoutActivity.class);

        // Launch activity change demand
        startActivity(AddWorkoutViewIntent);
    }

    public void startEditActivity(Workout workout){
        // Create new intent
        Intent EditWorkoutIntent = new Intent(MainActivity.this, SaveWorkoutActivity.class);

        EditWorkoutIntent.putExtra("workout", workout);
        EditWorkoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Launch activity change demand
        startActivity(EditWorkoutIntent);

        // Message
        Toast.makeText(MainActivity.this, "Edition de : " + workout.getName(), Toast.LENGTH_SHORT).show();
    }

    private void getWorkouts() {
        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class GetWorkouts extends AsyncTask<Void, Void, List<Workout>> {

            @Override
            protected List<Workout> doInBackground(Void... voids) {
                return mDb.getAppDatabase()
                        .workoutDao()
                        .getAll();
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

    private void findById(long id, final boolean isEdit, final boolean isDelete) {
        class FindById extends AsyncTask<Void, Void, Workout> {
            private long id;

            public void setId(long id) {
                this.id = id;
            }

            @Override
            protected Workout doInBackground(Void... voids) {
                Workout workout = mDb.getAppDatabase()
                        .workoutDao()
                        .findById(id);
                return workout;
            }

            @Override
            protected void onPostExecute(Workout workout) {
                super.onPostExecute(workout);
                if(isEdit) {
                    startEditActivity(workout);
                }
                if(isDelete) {
                    deleteWorkout(workout);
                }
            }
        }
        FindById findById = new FindById();
        findById.setId(id);
        findById.execute();
    }

    private void deleteWorkout(Workout workout) {
        class DeleteWorkout extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                mDb.getAppDatabase()
                        .workoutDao()
                        .delete(workout);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getWorkouts();
            }
        }
        DeleteWorkout deleteWorkout = new DeleteWorkout();
        deleteWorkout.execute();
    }

    public void onDeleteWorkout(long id){
        findById(id, false, true);
    }

    public void onUpdateWorkout(long id) {
        findById(id,true, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Mise à jour des entrainements
        getWorkouts();
    }

    public void onPlay(View view){
        int position = workoutList.getPositionForView(view);
        // Get the clicked workout thanks to adapter
        Workout workout = adapter.getItem(position);

        Intent WorkoutViewActivityIntent = new Intent(MainActivity.this, WorkoutActivity.class);

        WorkoutViewActivityIntent.putExtra("workout", workout);
        // Launch activity change demand
        startActivity(WorkoutViewActivityIntent);
        // Message
        Toast.makeText(MainActivity.this, workout.getName(), Toast.LENGTH_SHORT).show();
    }

    public void onMore(View view) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, view);

        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_actions, popup.getMenu());

        long position = (long) view.getTag();

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(
                        MainActivity.this,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();

                switch (item.getItemId()) {
                    case R.id.deleteWorkout:
                        onDeleteWorkout(Long.parseLong(view.getTag().toString()));
                        return true;
                    default:
                        onUpdateWorkout(Long.parseLong(view.getTag().toString()));
                        return false;
                }
            }
        });
        popup.show(); //showing popup
    }
}