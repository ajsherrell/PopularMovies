package com.example.android.popularmovies.model;

import android.widget.ImageView;

public class Movie {

    private String mOriginalTitle;
    private ImageView mPosterThumbnail;
    private String mPlotOverview;
    private String mUserRating;
    private String mReleaseDate;

    public Movie() {}

    public Movie(String originalTitle, ImageView posterThumbnail, String plotOverview,
                 String userRating, String releaseDate) {
        this.mOriginalTitle = originalTitle;
        this.mPosterThumbnail = posterThumbnail;
        this.mPlotOverview = plotOverview;
        this. mUserRating = userRating;
        this.mReleaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String OriginalTitle) {
        this.mOriginalTitle = OriginalTitle;
    }

    public ImageView getPosterThumbnail() {
        return mPosterThumbnail;
    }

    public void setPosterThumbnail(ImageView PosterThumbnail) {
        this.mPosterThumbnail = PosterThumbnail;
    }

    public String getPlotOverview() {
        return mPlotOverview;
    }

    public void setPlotOverview(String PlotOverview) {
        this.mPlotOverview = PlotOverview;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String UserRating) {
        this.mUserRating = UserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String ReleaseDate) {
        this.mReleaseDate = ReleaseDate;
    }
}
