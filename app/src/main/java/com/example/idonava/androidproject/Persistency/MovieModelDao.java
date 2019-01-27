package com.example.idonava.androidproject.Persistency;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.idonava.androidproject.MovieModel;

import java.util.List;

@Dao
public interface MovieModelDao {
    @Query("SELECT * FROM MovieModel  ORDER BY popularity DESC")
    List<MovieModel> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieModel... movieModels);

    @Query("DELETE FROM MovieModel")
    void deleteAll();

}
