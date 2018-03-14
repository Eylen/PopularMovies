package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.eylen.popularmovies.databinding.FragmentMovieInfoBinding;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.view.listener.MovieDetailActionListener;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieInfoFragment extends Fragment implements MovieDetailActionListener{
    private MovieDetailViewModel mModel;
    private FragmentMovieInfoBinding mBinding;

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieInfoFragment.
     */
    public static MovieInfoFragment newInstance() {
        return new MovieInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMovieInfoBinding.inflate(inflater, container, false);
        mBinding.setHandler(this);
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

    @Override
    public void changeFavoriteState(Movie movie) {
        mModel.updateFavoriteState(!movie.isFavored());
    }
}
