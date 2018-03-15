package es.eylen.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eylen on 14/03/2018.
 */

public class ReviewsContract {
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + MoviesContract.CONTENT_AUTHORITY);
    public static final String PATH_REVIEWS = "reviews";

    public static final class ReviewEntry implements BaseColumns{
        public static final String TABLE_REVIEWS = "reviews";

        public static final String _ID = "_id";
        public static final String COLUMN_ID = "review_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_MOVIE = "movie_id";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_REVIEWS).build();
    }
}
