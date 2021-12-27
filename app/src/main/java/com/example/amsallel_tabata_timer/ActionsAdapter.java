package com.example.amsallel_tabata_timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amsallel_tabata_timer.data.Action;

import java.util.List;

public class ActionsAdapter extends ArrayAdapter<Action> {
    public ActionsAdapter(Context mCtx, List<Action> actionList) {
        super(mCtx, R.layout.template_action, actionList);
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
        final Action action = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_action, parent, false);

        // Récupération des objets graphiques dans le template
        TextView textAction = (TextView) rowView.findViewById(R.id.textAction);

        textAction.setText(action.getLabel()+" : "+action.getTimeValue());

        return rowView;
    }
}
