package es.eylen.popularmovies.view.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.utils.Constants;

/**
 * Created by eylen on 21/02/2018.
 */

public class ImageBindingAdapter {
    @BindingAdapter(value = "poster")
    public static void setThumbnail(ImageView view, String poster){
        Picasso.with(view.getContext())
                .load(Constants.POSTER_THUMBNAIL_URL + poster)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(view);
    }
}
