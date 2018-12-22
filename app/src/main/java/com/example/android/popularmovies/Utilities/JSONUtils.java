package com.example.android.popularmovies.Utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.model.Movie;

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

import javax.security.auth.login.LoginException;

public final class JSONUtils {

    //log tag
    private static final String TAG = JSONUtils.class.getSimpleName();

    //private constructor
    private JSONUtils() {}

    /**
     *
     * @param requestURL get url
     * @return movies
     */
    public static List<Movie> fetchMovieData(String requestURL) {
        // create URL object
        URL url = createUrl(requestURL);
        Log.i(TAG, "fetchMovieData is working!");

        // perform HTTP request to the URL and receive a JSON response back.
        String jsonRespone = null;
        try {
            jsonRespone = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "fetchMovieData: something is wrong here!!!!!!");
        }

        // extract relevant fields from JSON response and create list of objects
        List<Movie> movies = extractDataFromJson(jsonRespone);

        // return the list of {@link Movie} objects
        return movies;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "createUrl: Problem building the URL", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     * -- used from my Udacity project: NewsApp
     * @param url
     * @return
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
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
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
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
        }
        return output.toString();
    }

    private static List<Movie> extractDataFromJson(String moviesJSON) {
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

            // todo JSON body
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "extractDataFromJson: problem with parsing the JSON", e);
        }
        // return list of movie data
        return movies;

    }

}
