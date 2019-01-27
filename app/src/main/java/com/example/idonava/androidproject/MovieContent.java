package com.example.idonava.androidproject;

import java.util.ArrayList;

public class MovieContent {
    public static final ArrayList<MovieModel> MOVIES = new ArrayList<>();

    public static void clear() {
        MOVIES.clear();
    }

    public static void addMovie(MovieModel mv) {
        if (!MOVIES.contains(mv)){
            MOVIES.add(mv);
        }
    }
}

