package es.eylen.popularmovies.view.listener;

import es.eylen.popularmovies.service.model.Movie;

/**
 * Created by eylen on 12/03/2018.
 */

public interface MovieDetailActionListener {

    void changeFavoriteState(Movie movie);
}
