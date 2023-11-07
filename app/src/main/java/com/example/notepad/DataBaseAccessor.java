package com.example.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Класс для доступа к БД
    public class DataBaseAccessor extends SQLiteOpenHelper
    {
        // Основные данные базы
        private static final String DATABASE_NAME = "db.db";
        private static final int DB_VERSION = 3;

        // таблицы
        private static final String COMPUTER_DATA = "DATA";

        // столбцы таблицы Data
        private static final String COLUMN_ID = "_id";//Обязательно с подчеркиванием
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_STATUS = "status";
        private static final String COLUMN_LOCATION = "location";
        private static final String COLUMN_TIME = "timeExitNet";

        public DataBaseAccessor(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }