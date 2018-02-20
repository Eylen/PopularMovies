package es.eylen.popularmovies.view.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.service.model.Movie;

/**
 * Created by eylen on 16/02/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private MovieClickListener mClickListener;

    public MovieListAdapter(MovieClickListener movieClickListener) {
        this.mClickListener = movieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.setPoster(movie.getPoster());
        if (mClickListener != null) {
            holder.mPoster.setOnClickListener(view -> mClickListener.onClick(movie));
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public void setMovieList(final List<Movie> movieList){
        if (this.movieList == null){
            this.movieList = movieList;
            notifyItemRangeInserted(0, movieList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return MovieListAdapter.this.movieList.size();
                }

                @Override
                public int getNewListSize() {
                    return movieList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    final Movie oldMovie = MovieListAdapter.this.movieList.get(oldItemPosition);
                    final Movie newMovie = movieList.get(newItemPosition);
                    return oldMovie.getId() == newMovie.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    final Movie oldMovie = MovieListAdapter.this.movieList.get(oldItemPosition);
                    final Movie newMovie = movieList.get(newItemPosition);
                    return oldMovie.getId() == newMovie.getId();
                }
            });
            this.movieList.clear();
            this.movieList.addAll(movieList);
            result.dispatchUpdatesTo(this);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView mPoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.movie_item_poster);
        }

        void setPoster(String posterUrl){
            Picasso.with(mPoster.getContext()).load("http://image.tmdb.org/t/p/w185/" + posterUrl).into(mPoster);
        }
    }

    public interface MovieClickListener {
        void onClick(Movie movie);
    }
}
