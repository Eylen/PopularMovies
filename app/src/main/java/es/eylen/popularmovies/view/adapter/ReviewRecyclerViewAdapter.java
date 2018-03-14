package es.eylen.popularmovies.view.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.eylen.popularmovies.databinding.FragmentReviewBinding;
import es.eylen.popularmovies.service.model.Review;

/**
 * {@link RecyclerView.Adapter} that can display a {@link es.eylen.popularmovies.service.model.Review}
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder> {

    private List<Review> mValues;

    public ReviewRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    public void setValues(final List<Review> reviewList){
        if (this.mValues == null){
            this.mValues = reviewList;
            notifyItemRangeInserted(0, reviewList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ReviewRecyclerViewAdapter.this.mValues.size();
                }

                @Override
                public int getNewListSize() {
                    return reviewList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    final Review oldReview = ReviewRecyclerViewAdapter.this.mValues.get(oldItemPosition);
                    final Review newReview = reviewList.get(newItemPosition);
                    return Objects.equals(oldReview.getId(), newReview.getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    final Review oldReview = ReviewRecyclerViewAdapter.this.mValues.get(oldItemPosition);
                    final Review newReview = reviewList.get(newItemPosition);
                    return Objects.equals(oldReview.getId(), newReview.getId());
                }
            });
            this.mValues.clear();
            this.mValues.addAll(reviewList);
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentReviewBinding reviewBinding = FragmentReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(reviewBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Review review = mValues.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentReviewBinding mBinding;

        public ViewHolder(FragmentReviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Review review){
            mBinding.setReview(review);
            mBinding.executePendingBindings();
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBinding.getReview().getUrl() + "'";
        }
    }
}
