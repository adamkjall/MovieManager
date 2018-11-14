package com.adamk.moviemanager;

import android.os.AsyncTask;

import com.adamk.moviemanager.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchMovieTask extends AsyncTask<String, Void, String> {
    /**
     * Interface that enables FetchMoviesTask to communicate with other
     * classes that implement AsyncResponse
     */
    public interface AsyncResponse {
        void processFinish(List<Movie> output);
    }

    // Used to call on processFinish in mainActivity with the movies as an argument
    private AsyncResponse delegate;

    public FetchMovieTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getMovieData(strings);
    }

    @Override
    protected void onPostExecute(String data) {
        convertDataToListOfMovies(data);

    }


    private void convertDataToListOfMovies(String data) {
        List<Movie> movies = new ArrayList<>();
        try {
            // Convert string of data to a JSONArray
            JSONObject jObj = new JSONObject(data);
            JSONArray jsonArray = jObj.getJSONArray("results");
            // For every item in the array, make a Movie object out of it
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id;
                if (jsonObject.has("id")) {
                    id = jsonObject.getString("id");
                } else {
                    id = "Unavailable";
                }
                String title;
                if (jsonObject.has("title")) {
                    title = jsonObject.getString("title");
                } else {
                    title = "Unavailable";
                }
                String overview;
                if (jsonObject.has("overview")) {
                    overview = jsonObject.getString("overview");
                } else {
                    overview = "Unavailable";
                }
                String releaseDate;
                if (jsonObject.has("release_date")) {
                    releaseDate = jsonObject.getString("release_date");
                } else {
                    releaseDate = "Unavailable";
                }
                String voteAverage;
                if (jsonObject.has("vote_average")) {
                    voteAverage = jsonObject.getString("vote_average");
                } else {
                    voteAverage = "Unavailable";
                }
                String voteCount;
                if (jsonObject.has("vote_count")) {
                    voteCount = jsonObject.getString("vote_count");
                } else {
                    voteCount = "Unavailable";
                }
                String posterPath;
                if (jsonObject.has("poster_path")) {
                    posterPath = jsonObject.getString("poster_path");
                } else {
                    posterPath = "Unavailable";
                }
                String backdropPath;
                if (jsonObject.has("backdrop_path")) {
                    backdropPath = jsonObject.getString("backdrop_path");
                } else {
                    backdropPath = "Unavailable";
                }

                Movie movie = new Movie(id, title, overview, releaseDate, voteAverage,
                        voteCount, posterPath, backdropPath);
                movies.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (!movies.isEmpty()) {
                delegate.processFinish(movies);
            }
        }
    }
}
