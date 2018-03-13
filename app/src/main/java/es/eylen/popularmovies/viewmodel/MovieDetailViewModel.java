package es.eylen.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.model.Review;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.service.repository.MovieRepository;

/**
 * ViewModel for MovieDetail activity
 */
public class MovieDetailViewModel extends AndroidViewModel{
    private final MutableLiveData<Movie> mSelectedMovie;
    private final LiveData<Resource<List<Trailer>>> movieTrailersObservable;
    private final LiveData<Resource<List<Review>>> movieReviewsObservable;
    private final MovieRepository mRepository;

    public MovieDetailViewModel(Application application){
        super(application);
        mRepository = MovieRepository.getInstance(application);
        mSelectedMovie = new MutableLiveData<>();
        movieTrailersObservable = Transformations.switchMap(mSelectedMovie, this::getTrailers);
        movieReviewsObservable = Transformations.switchMap(mSelectedMovie, this::getReviews);
    }


    public void select(Movie movie){
        boolean isFavorite = mRepository.loadMovieFavoriteState(movie.getId());
        movie.setFavored(isFavorite);
        mSelectedMovie.setValue(movie);
    }

    public LiveData<Movie> getSelected(){
        return this.mSelectedMovie;
    }

    public LiveData<Resource<List<Trailer>>> getMovieTrailersObservable() {
        return movieTrailersObservable;
    }

    public LiveData<Resource<List<Review>>> getMovieReviewsObservable() {
        return movieReviewsObservable;
    }

    private LiveData<Resource<List<Trailer>>> getTrailers(Movie movie){
        return mRepository.loadMovieTrailers(movie.getId());
    }

    private LiveData<Resource<List<Review>>> getReviews(Movie movie){
        return mRepository.loadMovieReviews(movie.getId());
    }

    public void updateFavoriteState(boolean isFavorite){
        Movie movie = mSelectedMovie.getValue();
        if (movie != null) {
            movie.setFavored(isFavorite);
            mRepository.updateMovieFavoriteState(movie.getId(), isFavorite);
            mSelectedMovie.postValue(movie);
        }
    }
}
