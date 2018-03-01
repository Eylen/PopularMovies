package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.eylen.popularmovies.databinding.FragmentMovieSynopsisBinding;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieSynopsisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieSynopsisFragment extends Fragment {
    MovieDetailViewModel mModel;
    private FragmentMovieSynopsisBinding mBinding;

    public MovieSynopsisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieSynopsisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieSynopsisFragment newInstance() {
        MovieSynopsisFragment fragment = new MovieSynopsisFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieSynopsisBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);
        mModel.getSelected().observe(this, this::populateUI);
    }

    private void populateUI(Movie movie){
        mBinding.setMovie(movie);
    }
}
