package es.eylen.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.repository.MovieRepository;

/**
 * ViewModel for MovieList activity
 */
public class MovieListViewModel extends ViewModel implements SwipeRefreshLayout.OnRefreshListener{
    private final LiveData<Resource<List<Movie>>> movieListObservable;
    private final MutableLiveData<Boolean> sortByPopularity;
    private final MovieRepository mRepository;

    public MovieListViewModel(){
        super();
        mRepository = MovieRepository.getInstance();
        sortByPopularity = new MutableLiveData<>();
        sortByPopularity.setValue(true);
        movieListObservable = Transformations.switchMap(sortByPopularity, this::getMovies);

    }

    public LiveData<Resource<List<Movie>>> getMovieListObservable(){
        return this.movieListObservable;
    }

    public void setSortByPopularity(boolean sortByPopularity){
        this.sortByPopularity.setValue(sortByPopularity);
    }

    private LiveData<Resource<List<Movie>>> getMovies(boolean sortByPopularity){
        return mRepository.loadMovieList(sortByPopularity);
    }

    @Override
    public void onRefresh() {
        this.sortByPopularity.setValue(this.sortByPopularity.getValue());
    }
}
