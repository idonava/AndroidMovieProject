package com.example.idonava.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.idonava.androidproject.Background.BGServiceActivity;
import com.example.idonava.androidproject.Networking.RestClient;
import com.example.idonava.androidproject.Networking.Result;
import com.example.idonava.androidproject.Networking.ResultsItem;
import com.example.idonava.androidproject.Networking.TrailerRespone;
import com.example.idonava.androidproject.Networking.VideosListResult;
import com.example.idonava.androidproject.Persistency.AppDatabase;
import com.example.idonava.androidproject.menu_activity.AsyncTaskActivity;
import com.example.idonava.androidproject.menu_activity.ThreadActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {
    RecyclerView recyclerView;
    MoviesViewAdapter movieAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MovieModel> movies;
   private  AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        appDatabase =  AppDatabase.getInstance(this);
        initMovies();
//        initRecyclerView();

    }

    public void setData(List<MovieModel> items) {
        movies.clear();
        movies.addAll(items);
        movieAdapter.notifyDataSetChanged();
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
        appDatabase.getMovieModelDao().deleteAll();
        movies = appDatabase.getMovieModelDao().getAll();
        if (movies==null || movies.isEmpty()){
             movies = MovieContent.MOVIES;
        }

        Call<VideosListResult> call = RestClient.moviesService.getPopularMovies();
        call.enqueue(new Callback<VideosListResult>() {
            @Override
            public void onResponse(Call<VideosListResult> call, Response<VideosListResult> response) {
                if (response.isSuccessful()) {
                    System.out.println(call.request());
                    System.out.println();
                    List<Result> a = response.body().getResults();
                    for (Result result : a) {
                       final MovieModel mm = new MovieModel();
                        mm.setMovieTitle(result.getTitle());
                        mm.setPosterPath(result.getPosterPath());
                        mm.setDate(result.getReleaseDate());
                        mm.setOverview(result.getOverview());
                        mm.setId(result.getId());
                        mm.setPopularity(result.getPopularity());
                        MovieContent.addMovie(mm);
                        appDatabase.getMovieModelDao().insertAll(mm);
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
        System.out.println(call.isExecuted() +" - "+ call.request());
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
