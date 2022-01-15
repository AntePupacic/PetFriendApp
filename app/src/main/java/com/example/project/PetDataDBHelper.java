package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PetDataDBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PetEntry.TABLE_NAME + " (" +
                    PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    PetEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    PetEntry.COLUMN_LOCATION + " TEXT NOT NULL," +
                    PetEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                    PetEntry.COLUMN_AGE + " TEXT NOT NULL," +
                    PetEntry.COLUMN_PHONE + " TEXT NOT NULL," +
                    PetEntry.COLUMN_DATE + " TEXT NOT NULL," +
                    PetEntry.COLUMN_IMAGE + " BLOB NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME;


    public PetDataDBHelper(@Nullable Context context) {
        super(context, PetEntry.DATABASE_NAME, null, PetEntry.DATABASE_VERSION);
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
