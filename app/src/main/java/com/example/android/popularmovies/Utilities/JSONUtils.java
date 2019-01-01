package com.example.android.popularmovies.Utilities;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.MainActivity;
import com.example.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class JSONUtils {

    //log tag
    private static final String TAG = JSONUtils.class.getSimpleName();

    Context context;

    // my api key
    private static final String MY_API_KEY = BuildConfig.API_KEY;
    //<<<<<<<<<<<<<<<<<<< get your own api key >>>>>>>>>>>>>>>>
    private static final String API_KEY = "api_key";

    private static final String MOVIE_DATABASE_BASE_URL = "https://api.themoviedb.org/3/movie/";

    // JSON constants
    private static final String RESULTS = "results";

    // query strings
    private static final String LANGUAGE = "language";
    private static final String MY_LANGUAGE = "en-US";

    public static final String SORT_BY_POPULAR = "popular";
    public static final String SORT_BY_RATING = "top_rated";
    public static final String SORT_BY_ID = Movie.getMovieId();

    //private constructor
    private JSONUtils() {}

    /**
     *
     * @param requestURL get url
     * @return movies
     */
    public static List<Movie> fetchMovieData(Context context, String requestURL) {
        // create URL object
        URL url = createUrl(requestURL);
        Log.d(TAG, "fetchMovieData is working!");

        // perform HTTP request to the URL and receive a JSON response back.
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "fetchMovieData: something is wrong here!!!!!!");
        }

        // extract relevant fields from JSON response and create list of objects
        List<Movie> movies = extractDataFromJson(context, jsonResponse);

        // return the list of {@link Movie} objects
        return movies;
    }

    public static URL createUrl(String sortBy) {
        // figure out which way to sort
        //sortBy = sortedMovie();
        //build the URI
        Uri builtUri = Uri.parse(MOVIE_DATABASE_BASE_URL + sortBy).buildUpon()
                .appendQueryParameter(API_KEY, MY_API_KEY)
                .appendQueryParameter(LANGUAGE, MY_LANGUAGE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(TAG, "createUrl: Problem building the URL", e);
        }

        Log.d(TAG, "createUrl: is this right??" + builtUri);
        Log.d(TAG, "createUrl: what is sortBy??" + sortBy);
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
        String jsonResponse = "";

        // if the URL is null, then return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.d(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.d(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
        }

        private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            Log.d(TAG, "readFromStream: problem with inputStream!!!!!!");
        }
        return output.toString();
    }

    public static List<Movie> extractDataFromJson(Context context, String moviesJSON) {

        // if the JSON string is empty or null, then return early
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        //create an empty ArrayList to add movies to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(moviesJSON);

            // extract the array "results" key from a JSONObject
            JSONArray jsonResultsArray = baseJsonResponse.getJSONArray(RESULTS);

            // for each movie in the jsonResultsArray, create and
            // {@link Movie} object
            for (int i = 0; i < jsonResultsArray.length(); i++) {


                // get single movie at position 1 in array list
                JSONObject currentMovie = jsonResultsArray.getJSONObject(i);

                // extract the value for the key called "id"
                String movieId = currentMovie.getString("id");

                // extract the value for the key called "original_title"
                String originalTitle = currentMovie.getString("original_title");

                // extract the value for the key called "poster_path"
                String posterThumbnail = currentMovie.getString("poster_path");

                // extract the value for the key called "overview"
                String plotOverview = currentMovie.getString("overview");

                // extract the value for the key called "vote_count"
                String userRating = currentMovie.getString("vote_count");

                // extract the value for the key called "release_date"
                String releaseDate = currentMovie.getString("release_date");

                // create a new {@link Movie} object with the JSON response
                Movie moviesList = new Movie(movieId, originalTitle, posterThumbnail,
                        plotOverview, userRating, releaseDate);

                // add the new {@link Movie} to the list of movies
                movies.add(moviesList);
                Log.d(TAG, "extractDataFromJson: movies:" + moviesList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "extractDataFromJson: problem with parsing the JSON", e);
        }
        // return list of movie data
        return movies;

    }

}
