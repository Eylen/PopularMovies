package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.databinding.ActivityMoviesBinding;
import es.eylen.popularmovies.service.helper.network.Status;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.utils.Constants;
import es.eylen.popularmovies.view.adapter.MovieListAdapter;
import es.eylen.popularmovies.viewmodel.MovieListViewModel;

public class MoviesActivity extends AppCompatActivity implements MovieListAdapter.MovieClickListener, LifecycleOwner,
        BottomNavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MoviesActivity";

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    private BottomNavigationView mBottomNavigationView;

    private RecyclerView mRecyclerView;
    private MovieListAdapter mMovieListAdapter;

    private MovieListViewModel mMovieListViewModel;

    private ActivityMoviesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        mMovieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);

        mBinding.setModel(mMovieListViewModel);

        mMovieListAdapter = new MovieListAdapter(this);
        mRecyclerView = findViewById(R.id.movie_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        observeViewModel(mMovieListViewModel);

        mBottomNavigationView = findViewById(R.id.bottom_detail_nav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void observeViewModel(MovieListViewModel viewModel){
        viewModel.getMovieListObservable().observe(this, moviesResource -> {
            if (moviesResource != null) {
                mBinding.setStatus(moviesResource.status);
                if (moviesResource.status == Status.SUCCESS && moviesResource.data != null) {
                    mMovieListAdapter.setMovieList(moviesResource.data);
                    mRecyclerView.scrollToPosition(0);
                }
            }
        });
    }

    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra(MovieDetailActivity.MOVIE_EXTRA, movie);
        startActivity(detailIntent);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String filterSortOption = null;
        switch (item.getItemId()){
            case R.id.movie_popular_nav:
                filterSortOption = Constants.SORT_BY_POPULARITY;
                break;
            case R.id.movie_top_rated_nav:
                filterSortOption = Constants.SORT_BY_TOP_RATED;
                break;
            case R.id.movie_favorites_nav:
                filterSortOption = Constants.FILTER_BY_FAVORITES;
                break;
        }
        mMovieListViewModel.setFilterSortOption(filterSortOption);
        return true;
    }
}
