package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.databinding.ActivityMovieDetailBinding;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

public class MovieDetailActivity extends AppCompatActivity implements LifecycleOwner{
    private static final String TAG = "MovieDetailActivity";

    public static final String MOVIE_EXTRA = "movie";

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    private MovieDetailViewModel mModel;

    private ActivityMovieDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        mModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mModel.getSelected().observe(this, movie -> populateUI(movie));

        if (getIntent().getExtras().containsKey(MOVIE_EXTRA)){
            mModel.select(getIntent().getParcelableExtra(MOVIE_EXTRA));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateUI(Movie movie){
        Log.d(TAG, "Populating movie: " + movie.toString());
        setTitle(movie.getOriginalTitle());

        mBinding.setMovie(movie);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }
}
