package com.example.generalassembly.stockapiapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by generalassembly on 3/7/16.
 */
public class CursorAdapter extends android.widget.CursorAdapter {

    public CursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.activity_main, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textNameAndSymbol = (TextView) view.findViewById(R.id.cursor_textview1);
        TextView textPrice = (TextView) view.findViewById(R.id.cursor_textview2);

        String symbol = cursor.getString(cursor.getColumnIndex(StockDBHelper.COL_STOCK_SYMBOL));
        String name = cursor.getString(cursor.getColumnIndex(StockDBHelper.COL_STOCK_NAME));

        textNameAndSymbol.setText(name + "("+symbol+")");
        textPrice.setText(cursor.getString(cursor.getColumnIndex(StockDBHelper.COL_PRICE)));
    }
}
