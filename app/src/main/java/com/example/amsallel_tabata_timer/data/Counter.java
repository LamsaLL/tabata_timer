package com.example.amsallel_tabata_timer.data;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;

public class Counter extends UpdateSource {
    // CONSTANTE
    private final static long INITIAL_TIME = 0;

    // DATA
    private long updatedTime;
    private CountDownTimer timer;   // https://developer.android.com/reference/android/os/CountDownTimer.html

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    private ArrayList<Action> actions;

    public Counter(long updatedTime, ArrayList<Action> actions) {
        this.updatedTime = updatedTime * 1000;
        this.actions = actions;
    }

    // Lancer le compteur
    public void start() {
            // Créer le CountDownTimer
            timer = new CountDownTimer(updatedTime, 100) {

                // Callback fired on regular interval
                public void onTick(long millisUntilFinished) {
                    updatedTime = millisUntilFinished;

                    // Mise à jour
                    update();
                }

                // Callback fired when the time is up
                public void onFinish() {
                    if(actions.get(0) != null){
                        actions.remove(0);
                        updatedTime = actions.get(0).getTimeValue() * 1000;
                        Counter.this.start();
                    }

                    // Mise à jour
                    update();
                }

            }.start();   // Start the countdown
    }

    // Mettre en pause le compteur
    public void pause() {

        if (timer != null) {

            // Arreter le timer
            stop();

            // Mise à jour
            update();
        }
    }


    // Remettre à le compteur à la valeur initiale
    public void reset() {

        if (timer != null) {

            // Arreter le timer
            stop();
        }

        // Réinitialiser
        updatedTime = INITIAL_TIME;

        // Mise à jour
        update();

    }

    // Arrete l'objet CountDownTimer et l'efface
    private void stop() {
        timer.cancel();
        timer = null;
    }

    public int getMinutes() {
        return (int) (updatedTime / 1000)/60;
    }

    public int getSecondes() {
        int secs = (int) (updatedTime / 1000);
        return secs % 60;
    }

    public int getMillisecondes() {
        return (int) (updatedTime % 1000);
    }

}
