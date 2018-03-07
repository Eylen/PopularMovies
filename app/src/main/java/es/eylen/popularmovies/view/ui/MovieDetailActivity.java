package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.databinding.ActivityMovieDetailBinding;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

public class MovieDetailActivity extends AppCompatActivity implements LifecycleOwner, BottomNavigationView.OnNavigationItemSelectedListener, MovieTrailerFragment.OnListFragmentInteractionListener{
    private static final String TAG = "MovieDetailActivity";

    public static final String MOVIE_EXTRA = "movie";

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    private ActivityMovieDetailBinding mBinding;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel.getSelected().observe(this, this::populateUI);

        if (getIntent().getExtras()!= null && getIntent().getExtras().containsKey(MOVIE_EXTRA)){
            viewModel.select(getIntent().getParcelableExtra(MOVIE_EXTRA));
        }

        mBottomNavigationView = findViewById(R.id.bottom_detail_nav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(MovieSynopsisFragment.newInstance());
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.movie_synopsis_nav:
                fragment = MovieSynopsisFragment.newInstance();
                break;
            case R.id.movie_reviews_nav:
                fragment = MovieReviewFragment.newInstance();
                break;
            case R.id.movie_trailers_nav:
                fragment = MovieTrailerFragment.newInstance();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.movie_detail_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Trailer item) {

    }
}
