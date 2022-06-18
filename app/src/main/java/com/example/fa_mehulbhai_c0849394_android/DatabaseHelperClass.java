package com.example.fa_mehulbhai_c0849394_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "placeDatabase";
    public static final String TABLE_NAME = "placeTable";

    public static final String KEY_ID = "id";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TITLE = "title";

    public DatabaseHelperClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                LATITUDE + " TEXT," +
                LONGITUDE + " TEXT," +
                TITLE + " TEXT)" ;

        sqLiteDatabase.execSQL(CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addPlaceToDatabase(ModelClass modelClass) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LATITUDE,modelClass.getPlaceLatitude());
        cv.put(LONGITUDE,modelClass.getPlaceLongitude());
        cv.put(TITLE,modelClass.getPlaceName());
        sqLiteDatabase.insert(TABLE_NAME,null,cv);
        sqLiteDatabase.close();
    }

    public List<ModelClass> getAllPlaces() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<ModelClass> placeList = new ArrayList<>();
        String getAllPlaceFromTable = "SELECT * FROM " + TABLE_NAME;
        Cursor c =sqLiteDatabase.rawQuery(getAllPlaceFromTable,null);
        if (c.moveToFirst()) {
            do {
                ModelClass modelClass = new ModelClass();
                modelClass.setId(c.getInt(0));
                modelClass.setPlaceLatitude(c.getString(1));
                modelClass.setPlaceLongitude(c.getString(2));
                modelClass.setPlaceName(c.getString(3));
                placeList.add(modelClass);
            } while (c.moveToNext());
        }
        return placeList;
    }
}

