package es.eylen.popularmovies.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.databinding.MovieListItemBinding;
import es.eylen.popularmovies.service.model.Movie;

/**
 * Recyclerview adapter to show movie list
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    private final MovieClickListener mClickListener;

    public MovieListAdapter(MovieClickListener movieClickListener) {
        this.mClickListener = movieClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_list_item, null, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie, mClickListener);
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public void setMovieList(final List<Movie> movieList){
        if (this.movieList == null){
            notifyDataSetChanged();
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
        private final MovieListItemBinding mBinding;

        MovieViewHolder(MovieListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Movie movie, MovieClickListener mListener){
            mBinding.setMovie(movie);
            mBinding.setListener(mListener);
            mBinding.executePendingBindings();
        }
    }

    public interface MovieClickListener {
        void onClick(Movie movie);
    }
}
