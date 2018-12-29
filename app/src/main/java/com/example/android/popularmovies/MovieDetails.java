package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.model.Movie;

public class MovieDetails extends AppCompatActivity {

    private TextView originalTitle;
    private ImageView moviePoster;
    private TextView plotOverview;
    private TextView userRating;
    private TextView releaseDate;

    private String moviePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        originalTitle = (TextView)findViewById(R.id.original_title);
        moviePoster = (ImageView)findViewById(R.id.movie_poster_image_thumbnail);
        plotOverview = (TextView)findViewById(R.id.plot_synopsis);
        userRating = (TextView)findViewById(R.id.user_rating);
        releaseDate = (TextView)findViewById(R.id.release_date);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                moviePage = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                originalTitle.setText(moviePage);

            }
        }

    }
}
