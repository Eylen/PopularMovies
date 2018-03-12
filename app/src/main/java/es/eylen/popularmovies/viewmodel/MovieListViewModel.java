package es.eylen.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.repository.MovieRepository;

/**
 * ViewModel for MovieList activity
 */
public class MovieListViewModel extends AndroidViewModel implements SwipeRefreshLayout.OnRefreshListener{
    private final LiveData<Resource<List<Movie>>> movieListObservable;
    private final MutableLiveData<Boolean> sortByPopularity;
    private final MutableLiveData<Boolean> showFavorites;
    private final MovieRepository mRepository;

    public MovieListViewModel(Application application){
        super(application);
        mRepository = MovieRepository.getInstance(application);
        sortByPopularity = new MutableLiveData<>();
        sortByPopularity.setValue(true);
        showFavorites = new MutableLiveData<>();
        showFavorites.setValue(false);
        movieListObservable = Transformations.switchMap(sortByPopularity, input -> getMovies(showFavorites.getValue(), input));

    }

    public LiveData<Resource<List<Movie>>> getMovieListObservable(){
        return this.movieListObservable;
    }

    public void setSortByPopularity(boolean sortByPopularity){
        this.sortByPopularity.setValue(sortByPopularity);
    }

    private LiveData<Resource<List<Movie>>> getMovies(boolean showFavorites, boolean sortByPopularity){
        return mRepository.loadMovieList(showFavorites, sortByPopularity);
    }

    @Override
    public void onRefresh() {
        this.sortByPopularity.setValue(this.sortByPopularity.getValue());
    }
}
