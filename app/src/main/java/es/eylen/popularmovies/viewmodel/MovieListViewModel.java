package es.eylen.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.repository.MovieRepository;

/**
 * Created by eylen on 16/02/2018.
 */

public class MovieListViewModel extends ViewModel {
    private final LiveData<List<Movie>> movieListObservable;

    public MovieListViewModel(){
        super();
        movieListObservable = MovieRepository.getInstance().getMovieList(false);
    }

    public LiveData<List<Movie>> getMovieListObservable(){
        return this.movieListObservable;
    }
}
