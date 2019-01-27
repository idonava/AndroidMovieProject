package com.example.idonava.androidproject.Persistency;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.idonava.androidproject.MovieModel;

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