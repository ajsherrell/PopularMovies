package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Utilities.JSONUtils;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;
import static com.example.android.popularmovies.Utilities.JSONUtils.createUrl;
import static com.example.android.popularmovies.Utilities.JSONUtils.extractDataFromJson;
import static com.example.android.popularmovies.Utilities.JSONUtils.fetchMovieData;
import static java.lang.String.*;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    Context context;

    private static final String TAG = MainActivity.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finders
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_movies);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mImageView = (ImageView)findViewById(R.id.movie_list_poster);


        LayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);

        // set if no UI change
        mRecyclerView.setHasFixedSize(true);

        // adapter links movie data
        mMovieAdapter = new MovieAdapter(this, new MovieAdapter.MovieAdapterOnClickHandler() {
            @Override
            public void onClick(List<Movie> clickedMovie) {
                // todo: what do I do here?
            }
        });

        // attach adapter to recyclerView
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showMoviePosterData();
        // todo json??
    }

    private void showMoviePosterData() {
        mErrorMessageDisplay.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(INVISIBLE);
        mErrorMessageDisplay.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(List<Movie> clickedMovie) {
        Intent intent = new Intent(MainActivity.this, MovieDetails.class);
        intent.putExtra(MovieDetails.DETAILS_INTENT, (Parcelable) clickedMovie);
        startActivity(intent);
    }

    //asyncTask
    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String movie = strings[0];
            URL movieRequestUrl = createUrl(valueOf(movie));

            try {
                String jsonMovieResponse = JSONUtils.makeHttpRequest(movieRequestUrl);

                List<Movie> simpleJsonMovieData = JSONUtils.extractDataFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: is not working right");
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
                Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + moviePosters)
                   .into(mImageView);
               MovieAdapter.setMovieData(moviePosters);
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
            //todo sort by popular movies
            //JSONUtils.createUrl(sortby ==);
        }

        if (id == R.id.action_rating) {
            //todo sort by rating
        }

        return super.onOptionsItemSelected(item);
    }
}
