package com.example.idonava.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.idonava.androidproject.Networking.RestClient;
import com.example.idonava.androidproject.Networking.Result;
import com.example.idonava.androidproject.Networking.ResultsItem;
import com.example.idonava.androidproject.Networking.TrailerRespone;
import com.example.idonava.androidproject.Networking.VideosListResult;
import com.example.idonava.androidproject.menu_activity.AsyncTaskActivity;
import com.example.idonava.androidproject.Background.BGServiceActivity;
import com.example.idonava.androidproject.menu_activity.ThreadActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {
    RecyclerView recyclerView;
    MoviesViewAdapter movieAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MovieModel> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initMovies();
//        initRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.taskActivity:
                //  AsyncTaskActivity ata = new AsyncTaskActivity();
                i = new Intent(this, AsyncTaskActivity.class);
                startActivity(i);
                break;

            case R.id.threadHandlerActivity:
                i = new Intent(this, ThreadActivity.class);
                startActivity(i);
                break;
            case R.id.BGServiceActivity:
                i = new Intent(this, BGServiceActivity.class);
                startActivity(i);
                break;

        }

        return false;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.movies_list);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        movieAdapter = new MoviesViewAdapter(movies, this, this);
        recyclerView.setAdapter(movieAdapter);
    }

    private void initMovies() {
        movies = MovieContent.MOVIES;

        Call<VideosListResult> call = RestClient.moviesService.getPopularMovies();

        call.enqueue(new Callback<VideosListResult>() {
            @Override
            public void onResponse(Call<VideosListResult> call, Response<VideosListResult> response) {
                if (response.isSuccessful()) {
                    System.out.println();
                    List<Result> a = response.body().getResults();
                    for (Result result : a) {
                       final MovieModel mm = new MovieModel();
                        mm.setMovieTitle(result.getTitle());
                        mm.setPosterPath(result.getPosterPath());
                        mm.setDate(result.getReleaseDate());
                        mm.setOverview(result.getOverview());
                        mm.setId(result.getId());
                        MovieContent.addMovie(mm);
                        final Call<TrailerRespone> callTrailer = RestClient.moviesService.getTrailer(mm.getId());
                     callTrailer.enqueue(new Callback<TrailerRespone>() {
                         @Override
                         public void onResponse(Call<TrailerRespone> call, Response<TrailerRespone> response) {
                            List<ResultsItem> list =  response.body().getResults();
                            if (!list.isEmpty()){
                                ResultsItem ri= list.get(0);
                                if (ri.getSite().equalsIgnoreCase("youtube") ){
                                    mm.setTrailerUrl("https://www.youtube.com/watch?v="+ri.getKey());
                                }
                            }

                         }

                         @Override
                         public void onFailure(Call<TrailerRespone> call, Throwable t) {

                         }
                     });

                    }
                    initRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<VideosListResult> call, Throwable t) {
                System.out.println(2);
            }
        });
        VideosListResult vlr = new VideosListResult();
    }

    @Override
    public void onMovieClicked(int itemPosition) {
        Log.d("sd", "aaa");
        if (itemPosition < 0 || itemPosition >= movies.size()) return;
        MovieModel movieModel = movies.get(itemPosition);
        if (movieModel == null || TextUtils.isEmpty(movieModel.getMovieTitle())) return;
        Toast.makeText(this, movieModel.getMovieTitle(), Toast.LENGTH_SHORT).show();
        Intent openDetails = new Intent(this, DetailsSlidePagerActivity.class);
        openDetails.putExtra(DetailsFragment.EXTRA_ITEM_POSITION, itemPosition);
        startActivity(openDetails);
    }


}
