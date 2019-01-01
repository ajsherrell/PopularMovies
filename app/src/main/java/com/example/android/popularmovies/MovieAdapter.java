package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private final Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;
    private static List<Movie> mMovies = new ArrayList<>();

    // interface for on click messages
    public interface MovieAdapterOnClickHandler {
        void onClick(List<Movie> clickedMovie);
    }

    /**
     * create MovieAdapter
     * @param clickHandler is called when the poster is clicked.
     */
    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.movie_list_poster);
            view.setOnClickListener(this);
        }

        /**
         *
         * @param view is the called view
         */
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            List<Movie> clickedMovies = Collections.singletonList(mMovies.get(adapterPosition));
            mClickHandler.onClick(clickedMovies);
            Log.e(TAG, "onClick: is not working!!!!");
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
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie currentMovie = mMovies.get(position);

        if (!TextUtils.isEmpty(currentMovie.getPosterThumbnail())) {
            Picasso.with(mContext)
                    .load(currentMovie.getPosterThumbnail())
                    .placeholder(R.drawable.baseline_camera_alt_black_18dp)
                    .error(R.drawable.baseline_error_outline_black_18dp)
                    .into(holder.mImageView);
        }
        Log.d(TAG, "onBindViewHolder: is not working!!!!" + currentMovie.getPosterThumbnail());
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public static void add(Movie movies) {
        mMovies.add(movies);
    }

    public void clear() {
        mMovies.clear();
    }

}
