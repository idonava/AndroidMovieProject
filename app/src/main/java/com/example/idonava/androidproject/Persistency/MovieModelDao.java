package com.example.idonava.androidproject.Persistency;


import com.example.idonava.androidproject.MovieModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MovieModelDao {
    @Query("SELECT * FROM MovieModel  ORDER BY popularity DESC")
    List<MovieModel> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieModel... movieModels);

    @Query("DELETE FROM MovieModel")
    void deleteAll();

}
