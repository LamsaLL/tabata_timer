package com.example.amsallel_tabata_timer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.amsallel_tabata_timer.db.Workout;

import java.util.List;

public class WorkoutsAdapter extends ArrayAdapter<Workout> {
    public WorkoutsAdapter(Context mCtx, List<Workout> workoutList) {
        super(mCtx, R.layout.template_workout, workoutList);
    }

    /**
     * Remplit une ligne de la listView avec les informations de la multiplication associée
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la multiplication
        final Workout workout = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_workout, parent, false);

        // Récupération des objets graphiques dans le template
        TextView textViewWorkoutTitle = (TextView) rowView.findViewById(R.id.textViewWorkoutTitle);
        TextView textViewPreparation = (TextView) rowView.findViewById(R.id.textViewPreparation);
        TextView textViewWorkTime = (TextView) rowView.findViewById(R.id.textViewWork);
        TextView textViewRestTime = (TextView) rowView.findViewById(R.id.textViewRest);
        TextView textNumberOfCycles = (TextView) rowView.findViewById(R.id.textViewCycle);
        TextView textNumberOfSets = (TextView) rowView.findViewById(R.id.textViewSet);
        TextView textRestBtwSetsTime = (TextView) rowView.findViewById(R.id.textViewRestBtwSet);
        ImageButton actionMoreButtonView = (ImageButton) rowView.findViewById(R.id.moreActions);

        textViewWorkoutTitle.setText(workout.getName());
        textViewPreparation.setText("Préparation: " + workout.getPreparationTime() + " secs");
        textViewWorkTime.setText("Travail: " + workout.getWorkTime() + " secs");
        textViewRestTime.setText("Repos: " + workout.getRestTime() + " secs");
        textNumberOfCycles.setText("Nombre de cycles: " + workout.getNumberOfCycles() );
        textNumberOfSets.setText("Nombre de sets: " + workout.getNumberOfSets() );
        textRestBtwSetsTime.setText("Repos entre les sets: " + workout.getRestBtwSetsTime() + " secs");
        actionMoreButtonView.setTag(workout.getId());

        return rowView;
    }
}
