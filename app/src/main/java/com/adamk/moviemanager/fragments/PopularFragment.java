package com.adamk.moviemanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adamk.moviemanager.FetchMovieTask;
import com.adamk.moviemanager.R;
import com.adamk.moviemanager.adapters.MovieRecyclerViewAdapter;
import com.adamk.moviemanager.models.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment implements FetchMovieTask.AsyncResponse {

    @BindView(R.id.rvPopular)
    RecyclerView rvMovies;
    private MovieRecyclerViewAdapter adapter;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this, view);

        int spanCount = 2;
        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        rvMovies.setHasFixedSize(true);

        adapter = new MovieRecyclerViewAdapter(getContext());
        rvMovies.setAdapter(adapter);

        new FetchMovieTask(this).execute("Popular");

        return view;
    }

    @Override
    public void processFinish(List<Movie> output) {
        adapter.setData(output);
    }
}
