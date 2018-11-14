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
public class DiscoverFragment extends Fragment implements FetchMovieTask.AsyncResponse {
    @BindView(R.id.rvDiscover)
    RecyclerView rvDiscover;
    private MovieRecyclerViewAdapter adapter;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);

        int spanCount = 2;
        rvDiscover.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        rvDiscover.setHasFixedSize(true);

        adapter = new MovieRecyclerViewAdapter(getContext());
        rvDiscover.setAdapter(adapter);

        new FetchMovieTask(this).execute("Discover");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void processFinish(List<Movie> output) {
        adapter.setData(output);
    }
}
