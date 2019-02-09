package com.example.idonava.androidproject.Persistency;


import android.content.Context;

import com.example.idonava.androidproject.MovieModel;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {MovieModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    static AppDatabase instance=null;
    public  static  AppDatabase getInstance(Context context){

        if (instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "movies-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract MovieModelDao getMovieModelDao();
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}