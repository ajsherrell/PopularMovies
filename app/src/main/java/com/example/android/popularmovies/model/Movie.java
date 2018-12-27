package com.example.android.popularmovies.model;


public class Movie {

    private int mMovieId;
    private String mOriginalTitle;
    private String mPosterThumbnail;
    private String mPlotOverview;
    private String mUserRating;
    private String mReleaseDate;

    public Movie() {}

    public Movie(int MovieID, String originalTitle, String posterThumbnail, String plotOverview,
                 String userRating, String releaseDate) {
        this.mMovieId = MovieID;
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

    public String getPosterThumbnail() {
        return mPosterThumbnail;
    }

    public void setPosterThumbnail(String PosterThumbnail) {
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

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int MovieId) {
        this.mMovieId = MovieId;
    }
}
