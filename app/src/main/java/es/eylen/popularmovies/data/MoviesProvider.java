package es.eylen.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by eylen on 09/03/2018.
 */

public class MoviesProvider extends ContentProvider {
    public static final int CODE_MOVIES = 100;
    public static final int CODE_SINGLE_MOVIE = 101;
    public static final int CODE_REVIEWS = 200;
    public static final int CODE_TRAILERS = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDBHelper mMoviesHelper;

    public static UriMatcher buildUriMatcher(){
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_MOVIES, CODE_MOVIES);
        matcher.addURI(authority, MoviesContract.PATH_MOVIES + "/#", CODE_SINGLE_MOVIE);

        matcher.addURI(authority, ReviewsContract.PATH_REVIEWS + "/#", CODE_REVIEWS);
        matcher.addURI(authority, TrailersContract.PATH_TRAILERS+ "/#", CODE_TRAILERS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMoviesHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        //TODO refactor
        final SQLiteDatabase db = mMoviesHelper.getWritableDatabase();
        int rowsInserted = 0;
        switch (sUriMatcher.match(uri)){
            case CODE_MOVIES:
                db.beginTransaction();
                try {
                    for (ContentValues value : values){
                        long _id = db.insertWithOnConflict(MoviesContract.MovieEntry.TABLE_MOVIES, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1){
                            rowsInserted++;
                        } else {
                            int updated = db.update(MoviesContract.MovieEntry.TABLE_MOVIES, value, MoviesContract.MovieEntry.COLUMN_ID + " = ?",
                                    new String[]{String.valueOf(value.getAsInteger(MoviesContract.MovieEntry.COLUMN_ID))});
                            if (updated > 0){
                                rowsInserted++;
                            }
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            case CODE_TRAILERS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values){
                        long _id = db.insertWithOnConflict(TrailersContract.TrailerEntry.TABLE_TRAILERS, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1){
                            rowsInserted++;
                        } else {
                            int updated = db.update(TrailersContract.TrailerEntry.TABLE_TRAILERS, value, TrailersContract.TrailerEntry.COLUMN_ID + " = ?",
                                    new String[]{String.valueOf(value.getAsInteger(TrailersContract.TrailerEntry.COLUMN_ID))});
                            if (updated > 0){
                                rowsInserted++;
                            }
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            case CODE_REVIEWS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values){
                        long _id = db.insertWithOnConflict(ReviewsContract.ReviewEntry.TABLE_REVIEWS, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1){
                            rowsInserted++;
                        } else {
                            int updated = db.update(ReviewsContract.ReviewEntry.TABLE_REVIEWS, value, ReviewsContract.ReviewEntry.COLUMN_ID + " = ?",
                                    new String[]{String.valueOf(value.getAsInteger(ReviewsContract.ReviewEntry.COLUMN_ID))});
                            if (updated > 0){
                                rowsInserted++;
                            }
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //TODO refactor
        Cursor cursor;
        final SQLiteDatabase db = mMoviesHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)){
            case CODE_MOVIES:
                cursor = db.query(MoviesContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_SINGLE_MOVIE:
                cursor = db.query(MoviesContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        MoviesContract.MovieEntry.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_TRAILERS:
                cursor = db.query(TrailersContract.TrailerEntry.TABLE_TRAILERS,
                        projection,
                        TrailersContract.TrailerEntry.COLUMN_MOVIE + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_REVIEWS:
                cursor = db.query(ReviewsContract.ReviewEntry.TABLE_REVIEWS,
                        projection,
                        ReviewsContract.ReviewEntry.COLUMN_MOVIE + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("getType not allowed");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        throw new UnsupportedOperationException("Single insert not allowed. Please use bulkinsert");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Delete not allowed");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String whereClause, @Nullable String[] whereValues) {
        SQLiteDatabase db = mMoviesHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (sUriMatcher.match(uri)){
            case CODE_SINGLE_MOVIE:
                rowsUpdated = db.update(MoviesContract.MovieEntry.TABLE_MOVIES,
                        contentValues,
                        whereClause,
                        whereValues);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public void shutdown() {
        mMoviesHelper.close();
        super.shutdown();
    }
}
