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
                + MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_VOTE_COUNT +  " INTEGER NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                + MoviesContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL,"
                + MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL"
                + ")";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
