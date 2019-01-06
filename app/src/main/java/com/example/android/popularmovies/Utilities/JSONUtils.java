package com.example.android.popularmovies.Utilities;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public final class JSONUtils {

    //log tag
    private static final String TAG = JSONUtils.class.getSimpleName();

    Context context;

    // my api key
    private static final String MY_API_KEY = BuildConfig.API_KEY;
    //<<<<<<<<<<<<<<<<<<< get your own api key >>>>>>>>>>>>>>>>
    private static final String API_KEY = "api_key";

    private static final String MOVIE_DATABASE_BASE_URL = "https://api.themoviedb.org/3/movie";

    // JSON constants
    private static final String RESULTS = "results";

    public static final String POSTER_SIZE_THUMBNAIL = "w185/";

    public static final String POSTER_SIZE_REGULAR = "w500/";

    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    // query strings
    private static final String LANGUAGE = "language";
    private static final String MY_LANGUAGE = "en-US";

    public static final String SORT_BY_POPULAR = "popular";
    public static final String SORT_BY_RATING = "top_rated";

    //private constructor
    private JSONUtils() {}


    public static URL createUrl(String sortBy) {
        //build the URI
        Uri builtUri = Uri.parse(MOVIE_DATABASE_BASE_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(API_KEY, MY_API_KEY)
                .appendQueryParameter(LANGUAGE, MY_LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "createUrl: Problem building the URL", e);
        }

        Log.d(TAG, "createUrl: is this right??" + builtUri);
        Log.d(TAG, "createUrl: what is sortBy??" + SORT_BY_POPULAR);
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     * -- used from my Udacity project: NewsApp
     * @param url
     *
     * @throws IOException
     */
    public static String makeHttpRequest(URL url) throws IOException {
       HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
       try {
           InputStream in = urlConnection.getInputStream();

           Scanner scanner = new Scanner(in);
           scanner.useDelimiter("\\A");

           boolean hasInput = scanner.hasNext();
           if (hasInput) {
               return scanner.next();
           } else {
               return null;
           }
       } finally {
           urlConnection.disconnect();
       }
    }



    public static ArrayList<Movie> extractDataFromJson(Context context, String moviesJSON) {

        // if the JSON string is empty or null, then return early
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        //create an empty ArrayList to add movies to
        ArrayList<Movie> data = new ArrayList<>();
        String[] movies = null;

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(moviesJSON);

            // extract the array "results" key from a JSONObject
            JSONArray jsonResultsArray = baseJsonResponse.getJSONArray(RESULTS);

            movies = new String[jsonResultsArray.length()];

            // for each movie in the jsonResultsArray, create and
            // {@link Movie} object
            for (int i = 0; i < jsonResultsArray.length(); i++) {


                // get single movie at position 1 in array list
                JSONObject currentMovie = jsonResultsArray.getJSONObject(i);

                // extract the value for the key called "original_title"
                String originalTitle = currentMovie.getString("original_title");

                // extract the value for the key called "poster_path"
                String posterThumbnail = currentMovie.getString("poster_path");

                // extract the value for the key called "overview"
                String plotOverview = currentMovie.getString("overview");

                // extract the value for the key called "vote_count"
                String userRating = currentMovie.getString("vote_average");

                // extract the value for the key called "release_date"
                String releaseDate = currentMovie.getString("release_date");

                // create a new {@link Movie} object with the JSON response
                Movie moviesList = new Movie(originalTitle, posterThumbnail,
                        plotOverview, userRating, releaseDate);

                data.add(moviesList);

                // add the new {@link Movie} to the list of movies
                movies[i] = String.valueOf(new ArrayList<Movie>());
                Log.d(TAG, "extractDataFromJson: movies:" + moviesList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "extractDataFromJson: problem with parsing the JSON", e);
        }
        // return list of movie data
        return data;

    }

}
