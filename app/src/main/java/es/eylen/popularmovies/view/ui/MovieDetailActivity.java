package es.eylen.popularmovies.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.databinding.ActivityMovieDetailBinding;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.utils.Constants;
import es.eylen.popularmovies.viewmodel.MovieDetailViewModel;

public class MovieDetailActivity extends AppCompatActivity implements LifecycleOwner,
        BottomNavigationView.OnNavigationItemSelectedListener, MovieTrailerFragment.OnListFragmentInteractionListener{
    private static final String TAG = "MovieDetailActivity";

    public static final String MOVIE_EXTRA = "movie";

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    private MovieDetailViewModel mViewModel;
    private ActivityMovieDetailBinding mBinding;

    private BottomNavigationView mBottomNavigationView;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mViewModel.getSelected().observe(this, this::populateUI);
        mViewModel.getMovieTrailersObservable().observe(this, listResource -> {
            if (listResource != null && listResource.data != null && listResource.data.size() > 0){
                setShareIntent(listResource.data.get(0));
            }
        });

        if (getIntent().getExtras()!= null && getIntent().getExtras().containsKey(MOVIE_EXTRA)){
            mViewModel.select(getIntent().getParcelableExtra(MOVIE_EXTRA));
        }

        mBottomNavigationView = findViewById(R.id.bottom_detail_nav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(MovieInfoFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        MenuItem itemShare = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(itemShare);
        return true;
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
                fragment = MovieInfoFragment.newInstance();
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
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Constants.YOUTUBE_SCHEME)
                .authority(Constants.YOUTUBE_BASE_URL)
                .path(Constants.YOUTUBE_WATCH_PATH)
                .appendQueryParameter("v", item.getKey());
        intent.setData(builder.build());

        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_PLAY_STORE)));
        }
    }

    private void setShareIntent(Trailer trailer){
        if (mShareActionProvider != null){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.YOUTUBE_SCHEME + "://" +Constants.YOUTUBE_BASE_URL + "/" + Constants.YOUTUBE_WATCH_PATH + "?w=" +trailer.getKey());
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
