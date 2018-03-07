package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.service.helper.network.Status;
import es.eylen.popularmovies.view.adapter.ReviewRecyclerViewAdapter;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

/**
 * A fragment representing a list of Items.
 */
public class MovieReviewFragment extends Fragment {
    private MovieDetailViewModel mModel;
    private ReviewRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieReviewFragment() {
    }

    @SuppressWarnings("unused")
    public static MovieReviewFragment newInstance() {
        return new MovieReviewFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);
        mModel.getMovieReviewsObservable().observe(this, reviewsResource -> {
            if (reviewsResource != null){
                if (reviewsResource.status == Status.SUCCESS && reviewsResource.data != null){
                    mAdapter.setValues(reviewsResource.data);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new ReviewRecyclerViewAdapter();
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }
}
