package es.eylen.popularmovies.view.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.view.ui.MovieTrailerFragment.OnListFragmentInteractionListener;
import es.eylen.popularmovies.view.ui.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Trailer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
