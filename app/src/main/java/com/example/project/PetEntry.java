package com.example.project;

import android.provider.BaseColumns;

public final class PetEntry implements BaseColumns {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PetData.db";
    public static final String TABLE_NAME = "pets";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DATE = "date";
}
