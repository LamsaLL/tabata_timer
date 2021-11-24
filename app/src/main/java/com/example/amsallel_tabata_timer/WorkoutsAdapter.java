package com.example.amsallel_tabata_timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        //TextView textViewTask = (TextView) rowView.findViewById(R.id.textViewTask);
        //TextView textViewDesc = (TextView) rowView.findViewById(R.id.textViewDesc);


        //textViewTask.setText(workout.getLibelle());
        //textViewDesc.setText(workout.getDescription());


        return rowView;
    }
}
