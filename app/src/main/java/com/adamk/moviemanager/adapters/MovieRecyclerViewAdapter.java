package com.adamk.moviemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adamk.moviemanager.R;
import com.adamk.moviemanager.activities.MovieDetailActivity;
import com.adamk.moviemanager.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MovieRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Movie> movies) {
        if (movies == null) return;
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie movie = movies.get(position);

        viewHolder.tvMovieTitle.setText(movie.getTitle());
        viewHolder.tvUserRating.setText(movie.getVoteAverage());
        viewHolder.tvReleaseDate.setText(movie.getReleaseYear());

        Log.d("Poster", "Posterpath: " + movie.getPosterPath());
        Picasso.with(context)
                .load(movie.getPosterPath())

                .into(viewHolder.ivMovieImage);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;
        @BindView(R.id.tvMovieTitle)
        TextView tvMovieTitle;
        @BindView(R.id.tvUserRating)
        TextView tvUserRating;
        @BindView(R.id.tvReleaseDate)
        TextView tvReleaseDate;
        @BindView(R.id.cvMovie)
        CardView cvMovie;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = movies.get(getAdapterPosition());

            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("MOVIE", movie);
            context.startActivity(intent);

        }
    }
}
