package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailActivity";

    public static final String MOVIE_EXTRA = "movie";

    private MovieDetailViewModel mModel;

    private ImageView mPoster;
    private TextView mYear;
    private TextView mRating;
    private TextView mSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewFields();

        mModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
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

    private void initViewFields(){
        mPoster = findViewById(R.id.movie_poster);
        mYear = findViewById(R.id.movie_year);
        mRating = findViewById(R.id.movie_rating);
        mSynopsis = findViewById(R.id.movie_synopsis);
    }

    private void populateUI(Movie movie){
        Log.d(TAG, "Populating movie: " + movie.toString());
        setTitle(movie.getOriginalTitle());

        Picasso.with(MovieDetailActivity.this).load("http://image.tmdb.org/t/p/w185/" + movie.getPoster()).into(mPoster);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movie.getReleaseDate());
        mYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        mRating.setText(movie.getVoteAverage() + "/10");
        mSynopsis.setText(movie.getSynopsis());
    }
}
