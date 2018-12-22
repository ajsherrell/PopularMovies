package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import static android.support.v7.widget.RecyclerView.*;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    // my api key
    private static final String MY_APT_KEY = "7019ac9f2a11e2696ae22a35ebf9b776";

    // URL from Movie database
    private static final String MOVIE_DATABASE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    // poster size
    private static final String SIZE = "";

    // the file_path
    private static final String FILE_PATH = "";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finders
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);

        LayoutManager layoutManager = new GridLayoutManager(this, 5);

        mRecyclerView.setLayoutManager(layoutManager);

        // set if no UI change
        mRecyclerView.setHasFixedSize(true);

        // adapter links movie data
        mMovieAdapter = new MovieAdapter(this);

        // attach adapter to recyclerview
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    @Override
    public void onClick(Image clickedMovie) {
        Context context = this;
        Class destinationClass = MovieDetails.class;
        Intent intentToStartMovieDetails = new Intent(context, destinationClass);
        intentToStartMovieDetails.putExtra(Intent.EXTRA_TEXT, (Parcelable) clickedMovie);
        startActivity(intentToStartMovieDetails);
    }

    private void loadMovieData() {
        showMoviePosterData();
        //todo - json ??
    }

    private void showMoviePosterData() {
        mErrorMessageDisplay.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(INVISIBLE);
        mErrorMessageDisplay.setVisibility(VISIBLE);
    }

    //asyncTask
    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            try {
                //todo json
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: is not working right");
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] moviePosters) {
            mProgressBar.setVisibility(INVISIBLE);
            if (moviePosters != null) {
                showMoviePosterData();
                Picasso.with(this, .load("http://i.imgur.com/Dvpvk1R.png")
                        .into(R.id.movie_list_poster);
            } else {
                showErrorMessage();
            }
        }
    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_popular) {
            //todo
        }

        if (id == R.id.action_rating) {
            //todo
        }

        return super.onOptionsItemSelected(item);
    }
}
