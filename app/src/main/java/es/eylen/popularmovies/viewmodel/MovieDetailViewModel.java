package es.eylen.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import es.eylen.popularmovies.service.model.Movie;

/**
 * Created by eylen on 20/02/2018.
 */

public class MovieDetailViewModel extends ViewModel {
    private MutableLiveData<Movie> mSelectedMovie;

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
