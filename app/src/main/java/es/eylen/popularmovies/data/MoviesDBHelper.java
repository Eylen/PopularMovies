package es.eylen.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by eylen on 09/03/2018.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "popularmovies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_MOVIES
                + "("
                + MoviesContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MoviesContract.MovieEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_VOTE_COUNT +  " INTEGER NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL,"
                + MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_FAVORITE + " INTEGER DEFAULT 0,"
                + " CONSTRAINT movie_id_unique UNIQUE (" + MoviesContract.MovieEntry.COLUMN_ID + ")"
                + ")";

        final String SQL_CREATE_TRAILERS_TABLE = "CREATE TABLE " + TrailersContract.TrailerEntry.TABLE_TRAILERS
                + "("
                + TrailersContract.TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TrailersContract.TrailerEntry.COLUMN_ID + " TEXT NOT NULL, "
                + TrailersContract.TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, "
                + TrailersContract.TrailerEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + TrailersContract.TrailerEntry.COLUMN_SITE + " TEXT NOT NULL, "
                + TrailersContract.TrailerEntry.COLUMN_TYPE +  " TEXT NOT NULL, "
                + TrailersContract.TrailerEntry.COLUMN_MOVIE + " INTEGER NOT NULL, "
                + " CONSTRAINT movie_id_unique UNIQUE (" + TrailersContract.TrailerEntry.COLUMN_ID + "),"
                + " FOREIGN KEY (" + TrailersContract.TrailerEntry.COLUMN_MOVIE + ") REFERENCES " + MoviesContract.MovieEntry.TABLE_MOVIES + "(" + MoviesContract.MovieEntry.COLUMN_ID + ")"
                + ")";

        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE " + ReviewsContract.ReviewEntry.TABLE_REVIEWS
                + "("
                + ReviewsContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReviewsContract.ReviewEntry.COLUMN_ID + " TEXT NOT NULL, "
                + ReviewsContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "
                + ReviewsContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, "
                + ReviewsContract.ReviewEntry.COLUMN_URL + " TEXT NOT NULL, "
                + ReviewsContract.ReviewEntry.COLUMN_MOVIE + " INTEGER NOT NULL, "
                + " CONSTRAINT movie_id_unique UNIQUE (" + ReviewsContract.ReviewEntry.COLUMN_ID + "),"
                + " FOREIGN KEY (" + ReviewsContract.ReviewEntry.COLUMN_MOVIE + ") REFERENCES " + MoviesContract.MovieEntry.TABLE_MOVIES + "(" + MoviesContract.MovieEntry.COLUMN_ID + ")"
                + ")";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILERS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
