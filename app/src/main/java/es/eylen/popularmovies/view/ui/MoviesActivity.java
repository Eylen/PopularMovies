package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.view.adapter.MovieListAdapter;
import es.eylen.popularmovies.viewmodel.MovieListViewModel;

public class MoviesActivity extends AppCompatActivity {
    private static final String TAG = "MoviesActivity";

    private RecyclerView mRecyclerView;
    private MovieListAdapter mMovieListAdapter;

    //TODO add loading indicator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mMovieListAdapter = new MovieListAdapter();
        mRecyclerView = findViewById(R.id.movie_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMovieListAdapter);
        mRecyclerView.setHasFixedSize(true);

        MovieListViewModel model = ViewModelProviders.of(this).get(MovieListViewModel.class);
        observeViewModel(model);
    }

    private void observeViewModel(MovieListViewModel viewModel){
        viewModel.getMovieListObservable().observe(this, movies -> {
            //TODO update UI
            if (movies != null) {
                mMovieListAdapter.setMovieList(movies);
            }
        });
    }
}
