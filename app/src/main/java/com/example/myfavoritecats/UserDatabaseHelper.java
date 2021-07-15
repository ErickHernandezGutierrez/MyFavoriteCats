package com.example.myfavoritecats;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class UserDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME    = "MyFavoriteCats.db";
    private static final int    DATABASE_VERSION = 1;

    private static final String TABLE_NAME    = "my_cats";
    private static final String COLUMN_ID     = "_id";
    private static final String COLUMN_API_ID = "mycatapi_id";
    private static final String COLUMN_URL    = "url";
    private static final String COLUMN_WIDTH  = "width";
    private static final String COLUMN_HEIGHT = "height";

    public UserDatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +  " (" +
                        COLUMN_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_API_ID + " TEXT, " +
                        COLUMN_URL    + " TEXT, " +
                        COLUMN_WIDTH  + " TEXT, " +
                        COLUMN_HEIGHT + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addCat(String catapi_id, String url, int width, int height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  cv = new ContentValues();

        cv.put(COLUMN_API_ID, catapi_id);
        cv.put(COLUMN_URL, url);
        cv.put(COLUMN_WIDTH, width);
        cv.put(COLUMN_HEIGHT, height);
        long result = db.insert(TABLE_NAME, null, cv);

        if( result == -1 ){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}