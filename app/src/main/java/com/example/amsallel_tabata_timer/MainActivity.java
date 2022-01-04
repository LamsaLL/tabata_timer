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
import android.widget.LinearLayout;
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
    private ImageButton moreActionButtonView;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get client database
        mDb = DatabaseClient.getInstance(getApplicationContext());

        // Get views
        moreActionButtonView = findViewById(R.id.moreActions);
        workoutList = findViewById(R.id.workoutList);
        buttonAdd = findViewById(R.id.button_add);

        // Link adapter to listView
        adapter = new WorkoutsAdapter(this, new ArrayList<Workout>());
        workoutList.setAdapter(adapter);

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

    private void findById(long id, final boolean startEditActivity, final boolean deleteAfterFind) {
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
                if(startEditActivity) {
                    startEditActivity(workout);
                }
                if(deleteAfterFind) {
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

    public void startEditActivity(Workout workout){
        // Create new intent
        Intent EditWorkoutActivityIntent = new Intent(MainActivity.this, AddWorkoutActivity.class);

        EditWorkoutActivityIntent.putExtra("workout", workout);
        EditWorkoutActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Launch activity change demand
        startActivity(EditWorkoutActivityIntent);

        // Message
        Toast.makeText(MainActivity.this, "Edit : " + workout.getName(), Toast.LENGTH_SHORT).show();

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
        Toast.makeText(MainActivity.this, "Click : " + workout.getName(), Toast.LENGTH_SHORT).show();
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

        popup.show(); //showing popup menu

    }
}