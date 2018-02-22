package es.eylen.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import es.eylen.popularmovies.service.model.Movie;

/**
 * ViewModel for MovieDetail activity
 */
public class MovieDetailViewModel extends ViewModel {
    private final MutableLiveData<Movie> mSelectedMovie;

    public MovieDetailViewModel(){
        super();
        mSelectedMovie = new MutableLiveData<>();
    }


    public void select(Movie movie){
        mSelectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelected(){
        return this.mSelectedMovie;
    }
}
