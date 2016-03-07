package com.example.generalassembly.stockapiapp;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private CursorAdapter mAdapter;
    private TextView mUpdatedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUpdatedTextView = (TextView) findViewById(R.id.textview);
        mAdapter = new CursorAdapter(this, getContentResolver().query(StockPriceContentProvider.CONTENT_URI, null, null, null, null), 0);

        getContentResolver().registerContentObserver(StockPriceContentProvider.CONTENT_URI, true, new StockContentObserver(new Handler()));
        //Handler communicates between threads
    }

    public class StockContentObserver extends ContentObserver{

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public StockContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mAdapter.swapCursor(getContentResolver().query(StockPriceContentProvider.CONTENT_URI, null, null, null, null));

            String currentDateAndTimeString = DateFormat.getDateTimeInstance().format(new Date());
            mUpdatedTextView.setText("Last Update: " + currentDateAndTimeString);
        }
    }
}
