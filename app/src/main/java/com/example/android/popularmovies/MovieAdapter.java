package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.model.Movie;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private final MovieAdapterOnClickHandler mClickHandler;
    private List<Movie> mMovies;

    // interface for on click messages
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie clickedMovie);
    }

    /**
     * create MovieAdapter
     * @param clickHandler is called when the poster is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.movie_poster_image_thumbnail);
            view.setOnClickListener(this);
        }

        /**
         *
         * @param view is the called view
         */
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie clickedMovies = mMovies.get(adapterPosition);
            mClickHandler.onClick(clickedMovies);
        }
    }

    /**
     *
     * @param viewGroup the view holders are here
     * @param viewType if more than one type
     * @return new MovieAdapterViewHolder that holds view for each grid item
     */
    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie currentMovie = mMovies.get(position);
        MovieAdapterViewHolder.mImageView.setImageResource(currentMovie);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public void setMovieData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }


}
