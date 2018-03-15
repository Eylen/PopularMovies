package es.eylen.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eylen on 14/03/2018.
 */

public class TrailersContract {
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + MoviesContract.CONTENT_AUTHORITY);
    public static final String PATH_TRAILERS = "trailers";

    public static final class TrailerEntry implements BaseColumns{
        public static final String TABLE_TRAILERS = "trailers";

        public static final String _ID = "_id";
        public static final String COLUMN_ID = "trailer_id";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_MOVIE = "movie_id";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_TRAILERS).build();
    }
}
