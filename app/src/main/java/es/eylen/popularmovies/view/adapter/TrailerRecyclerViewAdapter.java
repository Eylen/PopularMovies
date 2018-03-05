package es.eylen.popularmovies.view.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.eylen.popularmovies.databinding.FragmentTrailerBinding;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.view.ui.MovieTrailerFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trailer} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {

    private List<Trailer> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TrailerRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mValues = new ArrayList<>();
        mListener = listener;
    }

    public void setValues(final List<Trailer> trailerList){
        if (this.mValues == null){
            this.mValues = trailerList;
            notifyItemRangeInserted(0, trailerList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return TrailerRecyclerViewAdapter.this.mValues.size();
                }

                @Override
                public int getNewListSize() {
                    return trailerList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    final Trailer oldTrailer = TrailerRecyclerViewAdapter.this.mValues.get(oldItemPosition);
                    final Trailer newMovie = trailerList.get(newItemPosition);
                    return Objects.equals(oldTrailer.getId(), newMovie.getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    final Trailer oldTrailer = TrailerRecyclerViewAdapter.this.mValues.get(oldItemPosition);
                    final Trailer newTrailer = trailerList.get(newItemPosition);
                    return Objects.equals(oldTrailer.getId(), newTrailer.getId());
                }
            });
            this.mValues.clear();
            this.mValues.addAll(trailerList);
            result.dispatchUpdatesTo(this);
        }
    }
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentTrailerBinding binding = FragmentTrailerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrailerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int position) {
        Trailer trailer = mValues.get(position);
        holder.bind(trailer, mListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        private FragmentTrailerBinding mBinding;

        TrailerViewHolder(FragmentTrailerBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Trailer trailer, OnListFragmentInteractionListener listener){
            mBinding.setTrailer(trailer);
            mBinding.setListener(listener);
            mBinding.executePendingBindings();
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mBinding.getTrailer().getId() + "'";
        }
    }
}
