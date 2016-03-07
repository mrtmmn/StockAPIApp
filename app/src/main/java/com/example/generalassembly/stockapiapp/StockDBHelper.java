package com.example.generalassembly.stockapiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by generalassembly on 3/7/16.
 */
public class StockDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "STOCKS";
    public static final String TABLE_NAME = "STOCKS_TABLE";
    private static final int DATABASE_VERSION = 1;

    public static final String COL_ID = "_ID";
    public static final String COL_STOCK_SYMBOL = "STOCK_SYMBOL";
    public static final String COL_STOCK_NAME = "STOCK_NAME";
    public static final String COL_PRICE = "PRICE_REAL";
    public static final String [] All_COLUMNS = new String[] {COL_ID, COL_STOCK_SYMBOL, COL_STOCK_NAME, COL_PRICE};

    private static final String CREATE = "CREATE TABLE " + DATABASE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_STOCK_SYMBOL + " TEXT, " + COL_STOCK_NAME + " TEXT, "
            + COL_PRICE + " REAL)";

    public StockDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addStock(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long rowAdded = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowAdded;
    }

    public Cursor getAllStocks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                All_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public Cursor getStockById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                All_COLUMNS,
                COL_ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);

        return cursor;
    }

    public int updateStockById(String id, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsChanged = db.update(TABLE_NAME,
                values,
                COL_ID + " = ?",
                new String[]{id});
        db.close();

        return rowsChanged;
    }
}
