package es.eylen.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.repository.MovieRepository;

/**
 * Created by eylen on 16/02/2018.
 */

public class MovieListViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieListObservable;

    public MovieListViewModel(){
        super();
        movieListObservable = new MutableLiveData<>();
        MovieRepository.getInstance().loadMovieList(movieListObservable, true);
    }

    public LiveData<List<Movie>> getMovieListObservable(){
        return this.movieListObservable;
    }

    public void getMovies(boolean sortByPopularity){
        MovieRepository.getInstance().loadMovieList(movieListObservable, sortByPopularity);
    }
}
