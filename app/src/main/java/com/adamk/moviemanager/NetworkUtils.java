package com.adamk.moviemanager;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class NetworkUtils {
    private static final String BASE_URL_POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?api_key=ced2634ae9f8ce2a87af24cd8fde16aa";
    private static final String BASE_URL_DISCOVER_MOVIES = "https://api.themoviedb.org/3/discover/movie?api_key=ced2634ae9f8ce2a87af24cd8fde16aa";
    private static final String BASE_URL_TOP_RATED_MOVIES = "https://api.themoviedb.org/3/discover/movie?api_key=ced2634ae9f8ce2a87af24cd8fde16aa";

    // Parameters
    private static final String LANGUAGE_PARAM = "language";
    private static final String INCLUDE_ADULT_PARAM = "include_adult";
    private static final String PAGE_PARAM = "page";
    private static final String SORT_BY_PARAM = "sort_by";
    private static final String VOTE_COUNT_GTE_PARAM = "vote_count.gte";
    private static final String VOTE_AVERAGE_GTE_PARAM = "vote_average.gte";
    private static final String VOTE_AVERAGE_LTE_PARAM = "vote_avergae.lte";
    private static final String RELEASE_DATE_PARAM = "primary_release_date";

    // Values
    private static String language = "en-US";
    private static String includeAdultBoolean = "false";
    private static String page = "1";
    private static String sortBy = "popularity.desc";
    private static String voteCount = "100";
    private static String voteAverageMin = "7.0";
    private static String voteAverageMax = "8.0";
    private static String releaseDate = "2000-01-01";



    /**
     * Helper method for getMovieData, builds an URL with the parameters
     * language, includeAdultBoolean, page and sortByt
     *
     * @return URL to movie data
     * @throws MalformedURLException
     */
    private static URL buildURL(String baseURL) throws MalformedURLException {
        Uri builtUri = Uri.parse(baseURL)
                .buildUpon()
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(INCLUDE_ADULT_PARAM, includeAdultBoolean)
                .appendQueryParameter(PAGE_PARAM, page)
                .appendQueryParameter(SORT_BY_PARAM, sortBy)
                .appendQueryParameter(VOTE_COUNT_GTE_PARAM, voteCount)
                .appendQueryParameter(VOTE_AVERAGE_GTE_PARAM, voteAverageMin)
                .appendQueryParameter(RELEASE_DATE_PARAM, releaseDate)
                .build();
        return new URL(builtUri.toString());
    }

    /**
     * Fetch movie data from https://api.themoviedb.org
     *
     * @return JSON string with parking data
     */
    static String getMovieData(String... fetchType) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonString = "";
        try {
            // Setup URL connection
            URL url;
            switch (fetchType[0]) {
                case "Top rated":
                    url = buildURL(BASE_URL_TOP_RATED_MOVIES);
                    break;
                case "Discover":
                    url = buildURL(BASE_URL_DISCOVER_MOVIES);
                    break;
                default: // Default is popular movies
                    url = buildURL(BASE_URL_POPULAR_MOVIES);
                    break;
            }

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the input stream
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader for the input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a stringBuilder to hold the incoming response
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a new line isn't necessary (it won't
                // affect parsing) but it does make debugging a lot easier
                // if you print out the completed buffer for debugging
                //builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }
            jsonString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the connection & reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonString;
    }
}
