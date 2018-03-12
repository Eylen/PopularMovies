package es.eylen.popularmovies.view.bindingadapter;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;

import es.eylen.popularmovies.R;

/**
 * Created by eylen on 12/03/2018.
 */

public class FavoredButtonBindingAdapter {
    @BindingAdapter(value = "favored")
    public static void setDrawableImage(FloatingActionButton view, boolean isFavored){
        int drawable = R.drawable.ic_favorite_black_24dp;
        if (!isFavored){
            drawable = R.drawable.ic_favorite_border_black_24dp;
        }
        view.setImageDrawable(ContextCompat.getDrawable(view.getContext(),drawable));
    }
}
