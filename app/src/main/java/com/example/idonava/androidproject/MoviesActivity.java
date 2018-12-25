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

import com.example.idonava.androidproject.Networking.VideosListResult;
import com.example.idonava.androidproject.menu_activity.AsyncTaskActivity;
import com.example.idonava.androidproject.Background.BGServiceActivity;
import com.example.idonava.androidproject.menu_activity.ThreadActivity;

import java.util.ArrayList;

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
        initRecyclerView();

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
        VideosListResult vlr = new VideosListResult();

        movies = MovieContent.MOVIES;

        MovieModel movie1 = new MovieModel();
        MovieModel movie2 = new MovieModel();
        MovieModel movie3 = new MovieModel();
        MovieModel movie4 = new MovieModel();
        MovieModel movie5 = new MovieModel();
        MovieModel movie6 = new MovieModel();
        MovieModel movie7 = new MovieModel();
        MovieModel movie8 = new MovieModel();
        MovieModel movie9 = new MovieModel();

        movie1.setMovieTitle("Jurassic World - Fallen Kingdom");
        movie2.setMovieTitle("The Meg");
        movie3.setMovieTitle("The First Purge");
        movie4.setMovieTitle("Deadpool 2");
        movie5.setMovieTitle("Black Panther");
        movie6.setMovieTitle("Ocean's Eight");
        movie7.setMovieTitle("Interstellar");
        movie8.setMovieTitle("Thor - Ragnarok");
        movie9.setMovieTitle("Guardians of the Galaxy");

        movie1.setMainImage(R.drawable.jurassic_world_fallen_kingdom);
        movie2.setMainImage(R.drawable.the_meg);
        movie3.setMainImage(R.drawable.the_first_purge);
        movie4.setMainImage(R.drawable.deadpool_2);
        movie5.setMainImage(R.drawable.black_panther);
        movie6.setMainImage(R.drawable.ocean_eight);
        movie7.setMainImage(R.drawable.interstellar);
        movie8.setMainImage(R.drawable.thor_ragnarok);
        movie9.setMainImage(R.drawable.guardians_of_the_galaxy);

        movie1.setOverview("Three years after the demise of Jurassic World, a volcanic eruption threatens the remaining dinosaurs on the isla Nublar, so Claire Dearing, the former park manager, recruits Owen Grady to help prevent the extinction of the dinosaurs once again");
        movie2.setOverview("A deep sea submersible pilot revisits his past fears in the Mariana Trench, and accidentally unleashes the seventy foot ancestor of the Great White Shark believed to be extinct");
        movie3.setOverview("To push the crime rate below one percent for the rest of the year, the New Founding Fathers of America test a sociological theory that vents aggression for one night in one isolated community. But when the violence of oppressors meets the rage of the others, the contagion will explode from the trial-city borders and spread across the nation");
        movie4.setOverview("Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life");
        movie5.setOverview("King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') and an American secret agent, to prevent Wakanda from being dragged into a world war");
        movie6.setOverview("Debbie Ocean, a criminal mastermind, gathers a crew of female thieves to pull off the heist of the century at New York's annual Met Gala");
        movie7.setOverview("Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage");
        movie8.setOverview("Thor is on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela");
        movie9.setOverview("Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser");

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        movies.add(movie5);
        movies.add(movie6);
        movies.add(movie7);
        movies.add(movie8);
        movies.add(movie9);
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
