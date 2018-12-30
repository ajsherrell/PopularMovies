package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    public static final String DETAILS_INTENT = "com.example.android.popularmovies.details";

    private TextView originalTitle;
    private ImageView moviePoster;
    private TextView plotOverview;
    private TextView userRating;
    private TextView releaseDate;

    private Movie moviePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        moviePage = getIntent().getParcelableExtra(DETAILS_INTENT);

        originalTitle = (TextView)findViewById(R.id.original_title);
        moviePoster = (ImageView)findViewById(R.id.movie_poster_image_thumbnail);
        plotOverview = (TextView)findViewById(R.id.plot_synopsis);
        userRating = (TextView)findViewById(R.id.user_rating);
        releaseDate = (TextView)findViewById(R.id.release_date);

        populateUI();
        }

    private void populateUI() {
        originalTitle.setText(moviePage.getOriginalTitle());
        plotOverview.setText(moviePage.getPlotOverview());
        userRating.setText("User Rating " + moviePage.getUserRating());
        releaseDate.setText(moviePage.getReleaseDate());

        Picasso.with(this)
                .load(moviePage.getPosterThumbnail())
                .placeholder(R.drawable.baseline_camera_alt_black_18dp)
                .error(R.drawable.baseline_error_outline_black_18dp)
                .into(moviePoster);
    }

}

