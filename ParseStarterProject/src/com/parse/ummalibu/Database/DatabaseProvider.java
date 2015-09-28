package com.parse.ummalibu.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by rjaylward on 9/22/15.
 */
public class DatabaseProvider extends ContentProvider {

    //TODO consider renaming this completely for new release to avoid conflicts with the previous db structure
    //TODO or not, maybe versioning up is sufficient if we wipe it completely. just look into it

    public static final String AUTHORITY = "com.parse.ummalibu.provider.internalDB";
    public static final String DATABASE_NAME = "UM.db";
    public static final int DATABASE_VERSION = 1;

    private OpenDatabaseHelper mDatabaseHelper;

    private static final int RAW = 0;
    private static final int REQUESTS = 1;
    private static final int DRIVERS = 2;
    private static final int UM_LOCATIONS = 3;

    private static final UriMatcher mUriMatcher;
    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, Table.RAW, RAW);
        mUriMatcher.addURI(AUTHORITY, Table.Requests.TABLE_NAME, REQUESTS);
        mUriMatcher.addURI(AUTHORITY, Table.Drivers.TABLE_NAME, DRIVERS);
        mUriMatcher.addURI(AUTHORITY, Table.UmLocations.TABLE_NAME, UM_LOCATIONS);
    }

    public static class OpenDatabaseHelper extends SQLiteOpenHelper {

        private Context mContext;

        public OpenDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for(String tableName : TablesSQL.TABLE_NAMES) {
                db.execSQL(tableName);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            for(String tableName : TablesSQL.TABLE_NAMES) {
                db.execSQL(TablesSQL.deleteTable(tableName));
            }

            onCreate(db);
        }

    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new OpenDatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch(mUriMatcher.match(uri)) {
            case DRIVERS :
                return Table.Drivers.CONTENT_ITEM_TYPE;
            case REQUESTS :
                return Table.Requests.CONTENT_ITEM_TYPE;
            case UM_LOCATIONS :
                return Table.UmLocations.CONTENT_ITEM_TYPE;
            default :
                throw new IllegalArgumentException("uri not matched");
        }
    }

    /**
     * Get the table that the FILE_PATH represents
     *
     * @param uri content uri
     * @return string table name
     */
    public static String getTable(Uri uri) {
        switch(mUriMatcher.match(uri)) {
            case DRIVERS :
                return Table.Drivers.TABLE_NAME;
            case REQUESTS :
                return Table.Requests.TABLE_NAME;
            case UM_LOCATIONS :
                return Table.UmLocations.TABLE_NAME;
            default :
                throw new IllegalArgumentException("uri not matched");
        }
    }

    private int getInsertConflictResolution(Uri uri) {
//		switch(mUriMatcher.match(uri)) {
//			case EVENTS : //TODO check on this
        return SQLiteDatabase.CONFLICT_REPLACE;
//			default :
//				return SQLiteDatabase.CONFLICT_IGNORE;
//		}
    }

    @Override
    public synchronized Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        if(uri.equals(Table.RAW_QUERY))
            return db.rawQuery(selection, selectionArgs);
        else
            return db.query(getTable(uri), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public synchronized int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] initialValues) {
        String table = getTable(uri);
        int rows = 0;

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.beginTransaction();

        for(ContentValues values : initialValues) {
            try {
                long insertedId = db.insertWithOnConflict(table, null, values, getInsertConflictResolution(uri));
                if(insertedId >= 0)
                    rows++;
            } catch(SQLiteConstraintException e) {
                Log.e("bulk insert error", e.getMessage());
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        getContext().getContentResolver().notifyChange(uri, null);

        return rows;
    }

    @Override
    public synchronized Uri insert(@NonNull Uri uri, @NonNull ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String table = getTable(uri);

        long insertedId = db.insertWithOnConflict(table, null, values, getInsertConflictResolution(uri));

        if(insertedId < 0)
            Log.e("error", "inserted id: " + insertedId + " and was not caught");

        getContext().getContentResolver().notifyChange(uri, null);

        if(insertedId >= 0)
            return ContentUris.withAppendedId(uri, insertedId);
        else
            return null;
    }

    @Override
    public synchronized int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String table = getTable(uri);

        int count = db.delete(table, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public synchronized int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String table = getTable(uri);

        int count = db.update(table, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

//	public synchronized int bulkDelete(Uri uri, String where, Object[] args) {
//		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
//		String table = getTable(uri);
//
//		String sql = "DELETE FROM " + table + " WHERE " + where + " IN (" + HelpBot.printCommaSeparatedArray(args) + ")";
//
//		Cursor cursor = db.rawQuery(sql, null);
//
//		//TODO does this even work below?
//
//		try {
//			return cursor.getCount();
//		} finally {
//			cursor.close();
//		}
//
//	}

}
