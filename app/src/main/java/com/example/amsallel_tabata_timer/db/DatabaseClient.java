package com.example.amsallel_tabata_timer.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "Tabata").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
//        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "MyTabata").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

             db.execSQL("INSERT INTO workout (name, preparationTime, workTime, restTime, restBtwSetsTime, numberOfSets, numberOfCycles)" +
                     " VALUES(\"entrainement 1\", \"10\", \"20\",\"30\",\"5\",\"3\",\"2\");");
             db.execSQL("INSERT INTO workout (name, preparationTime, workTime, restTime, restBtwSetsTime, numberOfSets, numberOfCycles)" +
                     " VALUES(\"entrainement 2\", \"20\", \"10\",\"60\",\"10\",\"5\",\"8\");");

        }
    };
}
