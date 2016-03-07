package com.example.generalassembly.stockapiapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by generalassembly on 3/7/16.
 */
public class StockPriceContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.generalassembly.stockapiapp.StockPriceContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + StockDBHelper.TABLE_NAME);

    public static final int STOCK = 1;
    public static final int STOCK_ID = 2;
    //for uri's with an id on the end

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, StockDBHelper.TABLE_NAME, STOCK);
        sURIMatcher.addURI(AUTHORITY, StockDBHelper.TABLE_NAME + " /#", STOCK_ID);
    }
    //first time brought into memory, these will execute

    private StockDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new StockDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = sURIMatcher.match(uri);

        Cursor cursor;

        switch (uriType){
            case STOCK:
                cursor = mDbHelper.getAllStocks();
                break;
            case STOCK_ID:
                cursor = mDbHelper.getStockById(uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        //notify what's connecting uri to content provider that something has changed
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        long id = 0;
        switch (uriType) {
            case STOCK:
                id = mDbHelper.addStock(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);

        int rowsUpdated = 0;

        switch (uriType) {
            case STOCK_ID:
                rowsUpdated = mDbHelper.updateStockById(uri.getLastPathSegment(), values);
                break;
            default:
                throw new IllegalArgumentException("URI Unknown: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
