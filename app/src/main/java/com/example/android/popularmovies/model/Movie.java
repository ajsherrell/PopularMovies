package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

// used code from the Udacity AndroidFlavor example https://github.com/udacity/android-custom-arrayadapter/blob/parcelable/app/src/main/java/demo/example/com/customarrayadapter/AndroidFlavor.java
public class Movie implements Parcelable {

    private String mOriginalTitle;
    private int mPosterThumbnail;
    private String mPlotOverview;
    private String mUserRating;
    private String mReleaseDate;

    public Movie(String originalTitle, int posterThumbnail, String plotOverview, String userRating, String releaseDate) {
        this.mOriginalTitle = originalTitle;
        this.mPosterThumbnail = posterThumbnail;
        this.mPlotOverview = plotOverview;
        this. mUserRating = userRating;
        this.mReleaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mPosterThumbnail = in.readInt();
        mPlotOverview = in.readString();
        mUserRating = in.readString();
        mReleaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return mOriginalTitle + "--" + mPosterThumbnail + "--"
                + mPlotOverview + "--" + mUserRating + "--" + mReleaseDate;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOriginalTitle);
        parcel.writeInt(mPosterThumbnail);
        parcel.writeString(mPlotOverview);
        parcel.writeString(mUserRating);
        parcel.writeString(mReleaseDate);
    }

    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String OriginalTitle) {
        this.mOriginalTitle = OriginalTitle;
    }

    public int getPosterThumbnail() {
        return mPosterThumbnail;
    }

    public void setPosterThumbnail(int PosterThumbnail) {
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
