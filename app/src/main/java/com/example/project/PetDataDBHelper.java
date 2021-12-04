package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PetDataDBHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PetData.PetEntry.TABLE_NAME + " (" +
                    PetData.PetEntry._ID + " INTEGER PRIMARY KEY," +
                    PetData.PetEntry.COLUMN_NAME_NAME + " TEXT," +
                    PetData.PetEntry.COLUMN_NAME_LOCATION + " TEXT," +
                    PetData.PetEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    PetData.PetEntry.COLUMN_NAME_AGE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetData.PetEntry.TABLE_NAME;



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PetData.db";

    public PetDataDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
