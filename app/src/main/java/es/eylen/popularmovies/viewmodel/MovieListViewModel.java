package es.eylen.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.repository.MovieRepository;

/**
 * Created by eylen on 16/02/2018.
 */

public class MovieListViewModel extends ViewModel {
    private LiveData<Resource<List<Movie>>> movieListObservable;
    private MutableLiveData<Boolean> sortByPopularity;
    private MovieRepository mRepository;

    public MovieListViewModel(){
        super();
        mRepository = MovieRepository.getInstance();
        sortByPopularity = new MutableLiveData<>();
        sortByPopularity.setValue(true);
        movieListObservable = Transformations.switchMap(sortByPopularity,
                input -> getMovies(input));

    }

    public LiveData<Resource<List<Movie>>> getMovieListObservable(){
        return this.movieListObservable;
    }

    public void setSortByPopularity(boolean sortByPopularity){
        this.sortByPopularity.setValue(sortByPopularity);
    }

    public LiveData<Resource<List<Movie>>> getMovies(boolean sortByPopularity){
        return mRepository.loadMovieList(sortByPopularity);
    }
}
