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
import es.eylen.popularmovies.utils.Constants;

/**
 * ViewModel for MovieList activity
 */
public class MovieListViewModel extends AndroidViewModel implements SwipeRefreshLayout.OnRefreshListener{
    private final LiveData<Resource<List<Movie>>> movieListObservable;
    private final MutableLiveData<String> mFilterSortOption;
    private final MovieRepository mRepository;

    public MovieListViewModel(Application application){
        super(application);
        mRepository = MovieRepository.getInstance(application);
        mFilterSortOption = new MutableLiveData<>();
        mFilterSortOption.setValue(Constants.SORT_BY_POPULARITY);
        movieListObservable = Transformations.switchMap(mFilterSortOption, this::getMovies);
    }

    public LiveData<Resource<List<Movie>>> getMovieListObservable(){
        return this.movieListObservable;
    }

    public void setFilterSortOption(String filterSortOption){
        this.mFilterSortOption.setValue(filterSortOption);
    }

    private LiveData<Resource<List<Movie>>> getMovies(String filterSortOption){
        return mRepository.loadMovieList(filterSortOption);
    }

    @Override
    public void onRefresh() {
        this.mFilterSortOption.setValue(this.mFilterSortOption.getValue());
    }
}
