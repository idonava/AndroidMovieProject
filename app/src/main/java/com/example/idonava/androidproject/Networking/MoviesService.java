package com.example.idonava.androidproject.Networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {
    // https://api.themoviedb.org/3/search/movies?query=android
    @GET("movie/popular")
    Call<VideosListResult> searchMoviesByTitle(@Query("title") String title);

}