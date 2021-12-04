package com.example.project;

import android.provider.BaseColumns;

public final class PetData {

    private PetData(){}

    public static class PetEntry implements BaseColumns{
        public static final String TABLE_NAME = "pet";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_AGE = "age";
    }
}
