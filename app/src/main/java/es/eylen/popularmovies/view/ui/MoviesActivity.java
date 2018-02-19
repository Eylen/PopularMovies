package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.view.adapter.MovieListAdapter;
import es.eylen.popularmovies.viewmodel.MovieListViewModel;

public class MoviesActivity extends AppCompatActivity {
    private static final String TAG = "MoviesActivity";

    private RecyclerView mRecyclerView;
    private MovieListAdapter mMovieListAdapter;

    private static final int NUM_COLS = 2;
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

        MovieListViewModel model = ViewModelProviders.of(this).get(MovieListViewModel.class);
        observeViewModel(model);
    }

    private void observeViewModel(MovieListViewModel viewModel){
        viewModel.getMovieListObservable().observe(this, movies -> {
            if (movies != null) {
                mMovieListAdapter.setMovieList(movies);
            }
        });
    }
}
