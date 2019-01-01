package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
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
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;
import static com.example.android.popularmovies.Utilities.JSONUtils.SORT_BY_ID;
import static com.example.android.popularmovies.Utilities.JSONUtils.SORT_BY_POPULAR;
import static com.example.android.popularmovies.Utilities.JSONUtils.SORT_BY_RATING;
import static com.example.android.popularmovies.Utilities.JSONUtils.createUrl;
import static java.lang.String.*;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static Context context;


    private static RecyclerView mRecyclerView;
    private static MovieAdapter mMovieAdapter;
    private static TextView mErrorMessageDisplay;
    private static ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finders
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);


        GridLayoutManager layoutManager = new GridLayoutManager(context, numColumns());

        mRecyclerView.setLayoutManager(layoutManager);

        // set if no UI change
        mRecyclerView.setHasFixedSize(true);

        // adapter links movie data
        mMovieAdapter = new MovieAdapter(context, this);

        // attach adapter to recyclerView
        mRecyclerView.setAdapter(mMovieAdapter);

        onStart();
    }

    private void loadMovieData() {
        if (!isOnline()) {

        }
        showMoviePosterData();
        FetchMovieTask task = (FetchMovieTask) new FetchMovieTask();
        task.execute();
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
    public void onClick(List<Movie> clickedMovie) {
        Intent intent = new Intent(context, MovieDetails.class);
        intent.putExtra("Movie", (Parcelable) clickedMovie);
        startActivity(intent);
    }

    //asyncTask
    private static class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String movie = strings[0];
            URL movieRequestUrl = createUrl(valueOf(movie));

            try {
                String jsonMovieResponse = JSONUtils.makeHttpRequest(movieRequestUrl);

                List<Movie> simpleJsonMovieData = JSONUtils.extractDataFromJson(context, jsonMovieResponse);

                return simpleJsonMovieData;
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: is not working right");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(VISIBLE);
        }


        @Override
        protected void onPostExecute(List<Movie> moviePosters) {
            mProgressBar.setVisibility(INVISIBLE);
            if (moviePosters != null) {
                showMoviePosterData();
;                for (Movie movies : moviePosters) {
                    MovieAdapter.add(movies);
                }
            } else {
                showErrorMessage();
                Log.d(TAG, "onPostExecute: is not working!!!!!!");
            }
            mMovieAdapter.notifyDataSetChanged();
            super.onPostExecute(moviePosters);
            Log.d(TAG, "onPostExecute: something is wrong!!!!!");
        }
    }

    @Override
    protected void onStart() {
        loadMovieData();
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
        Log.d(TAG, "onOptionsItemSelected: something is wrong!!!");
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                createUrl(SORT_BY_ID);
                onStart();
                break;
            case R.id.action_popular:
                createUrl(SORT_BY_POPULAR);
                onStart();
                break;
            case R.id.action_rating:
                createUrl(SORT_BY_RATING);
                onStart();
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
