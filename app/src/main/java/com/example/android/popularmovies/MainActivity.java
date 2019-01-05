package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Utilities.JSONUtils;
import com.example.android.popularmovies.model.Movie;

import java.net.URL;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;
import static com.example.android.popularmovies.Utilities.JSONUtils.SORT_BY_POPULAR;
import static com.example.android.popularmovies.Utilities.JSONUtils.SORT_BY_RATING;
import static com.example.android.popularmovies.Utilities.JSONUtils.createUrl;
import static java.lang.String.*;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static RecyclerView mRecyclerView;
    private static MovieAdapter mMovieAdapter;
    private static TextView mErrorMessageDisplay;
    private static ProgressBar mProgressBar;
    ArrayList<Movie> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finders
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);


        GridLayoutManager layoutManager = new GridLayoutManager(this, numColumns());

        mRecyclerView.setLayoutManager(layoutManager);

        // set if no UI change
        mRecyclerView.setHasFixedSize(true);

        // adapter links movie data
        mMovieAdapter = new MovieAdapter(this, data, this);

        // attach adapter to recyclerView
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData(SORT_BY_POPULAR);
    }

    private void loadMovieData(String sortBy) {
            FetchMovieTask task = (FetchMovieTask) new FetchMovieTask();
            task.execute(sortBy);
            showMoviePosterData();

    }

    private static void showMoviePosterData() {
        mErrorMessageDisplay.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private static void showErrorMessage() {
        mRecyclerView.setVisibility(INVISIBLE);
        mErrorMessageDisplay.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(Movie clickedMovie) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("Movie", clickedMovie);
        startActivity(intent);
    }

    //asyncTask
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String movie = strings[0];
            URL movieRequestUrl = createUrl(valueOf(movie));

            try {
                String jsonMovieResponse = JSONUtils.makeHttpRequest(movieRequestUrl);

                data = JSONUtils.extractDataFromJson(MainActivity.this, jsonMovieResponse);

                return data;
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: is not working right");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieData) {
            mProgressBar.setVisibility(INVISIBLE);
            mMovieAdapter.clear();
            if (movieData != null) {
                showMoviePosterData();
                data = movieData;
                mMovieAdapter.add(movieData);
            } else {
                showErrorMessage();
                Log.d(TAG, "onPostExecute: is not working!!!!!!??");
            }
            mMovieAdapter.notifyDataSetChanged();
            super.onPostExecute(movieData);
            Log.d(TAG, "onPostExecute: something is wrong!!!!!" + movieData);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        if (!isOnline()) return false;
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                mMovieAdapter.add(null);
                loadMovieData(SORT_BY_POPULAR);
                mMovieAdapter.clear();
                Log.d(TAG, "onOptionsItemSelected: " + SORT_BY_POPULAR);
                break;
            case R.id.action_popular:
                loadMovieData(SORT_BY_POPULAR);
                mMovieAdapter.clear();
                Log.d(TAG, "onOptionsItemSelected: " + SORT_BY_POPULAR);
                break;
            case R.id.action_rating:
                loadMovieData(SORT_BY_RATING);
                mMovieAdapter.clear();
                Log.d(TAG, "onOptionsItemSelected: " + SORT_BY_RATING);
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // referenced https://developer.android.com/reference/android/util/DisplayMetrics
    private int numColumns() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // change this divider to adjust the size of poster
        int divider = 500;
        int width = metrics.widthPixels;
        int columns = width / divider;
        if (columns < 2) return 2;
        return columns;
    }

    // referenced https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

}
