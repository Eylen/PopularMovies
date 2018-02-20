package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.view.adapter.MovieListAdapter;
import es.eylen.popularmovies.viewmodel.MovieListViewModel;

public class MoviesActivity extends AppCompatActivity {
    private static final String TAG = "MoviesActivity";

    private RecyclerView mRecyclerView;
    private MovieListAdapter mMovieListAdapter;

    private MovieListViewModel mMovieListViewModel;

    private static final int NUM_COLS = 2;
    private boolean sortByPopularity = true;
    //TODO add loading indicator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mMovieListAdapter = new MovieListAdapter();
        mRecyclerView = findViewById(R.id.movie_list);

//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
//        layoutManager.setFlexDirection(FlexDirection.ROW);
//        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
//        layoutManager.setAlignItems(AlignItems.STRETCH);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mMovieListAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        mMovieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        observeViewModel(mMovieListViewModel);
    }

    private void observeViewModel(MovieListViewModel viewModel){
        viewModel.getMovieListObservable().observe(this, movies -> {
            if (movies != null) {
                mMovieListAdapter.setMovieList(movies);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_by_popularity:
                if (!sortByPopularity){
                    sortByPopularity = true;
                    mMovieListViewModel.getMovies(sortByPopularity);
                }
                break;
            case R.id.sort_by_top_rated:
                if (sortByPopularity){
                    sortByPopularity = false;
                    mMovieListViewModel.getMovies(sortByPopularity);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
