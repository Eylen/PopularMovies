package es.eylen.popularmovies.view.bindingadapter;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import es.eylen.popularmovies.R;
import es.eylen.popularmovies.utils.Constants;

/**
 * Binding Adapter to allow image loading with Picasso
 */
public class ImageBindingAdapter {
    @BindingAdapter(value = "poster")
    public static void setPoster(ImageView view, String poster){
        Picasso.with(view.getContext())
                .load(Constants.POSTER_THUMBNAIL_URL + poster)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(view);
    }

    @BindingAdapter(value = "backdrop")
    public static void setBackdrop(ImageView view, String poster){
        Picasso.with(view.getContext())
                .load(Constants.BACKDROP_THUMBNAIL_URL + poster)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(view);
    }

    @BindingAdapter(value = "thumbnail")
    public static void setThumbnail(ImageView view, String key){
        Uri.Builder builder = new Uri.Builder().scheme(Constants.YOUTUBE_IMAGE_SCHEME)
                .authority(Constants.YOUTUBE_IMAGE_BASE_URL)
                .appendPath(Constants.YOUTUBE_IMAGE_PATH)
                .appendPath(key)
                .appendPath(Constants.YOUTUBE_IMAGE_NAME);
        Picasso.with(view.getContext())
                .load(builder.build().toString())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(view);
    }
}
