package com.example.project;

import android.provider.BaseColumns;

public final class PetColumnsDB {

    private PetColumnsDB(){}

    public static class PetEntry implements BaseColumns{
        public static final String TABLE_NAME = "pet";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_AGE = "age";
    }
}
