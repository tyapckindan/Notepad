package com.example.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

    //Класс для доступа к БД
    public class DataBaseAccessor extends SQLiteOpenHelper {
    // Основные данные базы
    private static final String DATABASE_NAME = "db.db";
    private static final int DB_VERSION = 3;

    // таблицы
    private static final String TABLE_COMPUTERS = "COMPUTERS";

    // столбцы таблицы Data
    private static final String COLUMN_ID = "_id";//Обязательно с подчеркиванием
    private static final String COLUMN_NAME_COMPUTER = "name_computer";
    private static final String COLUMN_STATUS_COMPUTER = "status_computer";
    private static final String COLUMN_LOCATION_COMPUTER = "location_computer";
    private static final String COLUMN_TIME_COMPUTER = "timeExitNet_computer";

    public DataBaseAccessor(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создать таблицу
        db.execSQL("CREATE TABLE " + TABLE_COMPUTERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_COMPUTER + " TEXT,"
                + COLUMN_STATUS_COMPUTER + " TEXT,"
                + COLUMN_LOCATION_COMPUTER + "TEXT,"
                + COLUMN_TIME_COMPUTER + "TEXT);");

        // Добавить пару записей в таблицу
        db.execSQL("INSERT INTO " + TABLE_COMPUTERS + "(" + COLUMN_NAME_COMPUTER + ", " + COLUMN_STATUS_COMPUTER + ", " + COLUMN_LOCATION_COMPUTER + ", " + COLUMN_TIME_COMPUTER + ") values('name 1','status 1', 'location 1', 'time 1')");
        db.execSQL("INSERT INTO " + TABLE_COMPUTERS + "(" + COLUMN_NAME_COMPUTER + ", " + COLUMN_STATUS_COMPUTER + ", " + COLUMN_LOCATION_COMPUTER + ", " + COLUMN_TIME_COMPUTER + ") values('name 2','status 2', 'location 2', 'time 2')");
    }

    /**
     * получить адаптер для listview.
     *
     * @param layout  - разметка отдельного элемента listview
     * @param viewIds - идентификаторы элементов разметки ListView
     */
    public SimpleCursorAdapter getCursorAdapter(Context context, int layout, int[] viewIds) {
        // запрос данных для отображения
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_COMPUTERS, null);

        // какие столбцы и в каком порядке показывать в listview
        String[] columns = new String[]{COLUMN_NAME_COMPUTER, COLUMN_STATUS_COMPUTER, COLUMN_LOCATION_COMPUTER, COLUMN_TIME_COMPUTER};

        // создание адаптера
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, layout, cursor, columns, viewIds, 0);
        return adapter;
    }

    /**
     * Обновить данные в БД
     * @param id - идентификатор записи в БД
     * @param name_computer - имя компьютера
     * @param status_computer - статус компьютера
     * @param location_computer - местоположение компьютера
     * @param timeExitNet_computer - время выхода в сеть компьютера
     */
    public void updateComputers(int id, String name_computer, String status_computer, String location_computer, String timeExitNet_computer) {
        // выполнить запрос на обновление БД
        getReadableDatabase().execSQL("UPDATE " + TABLE_COMPUTERS
                + " SET "
                + COLUMN_NAME_COMPUTER + "='" + name_computer + "', "
                + COLUMN_STATUS_COMPUTER + "='" + status_computer + "',"
                + COLUMN_LOCATION_COMPUTER + "='" + location_computer + ","
                + COLUMN_TIME_COMPUTER + "='" + timeExitNet_computer + ""
                + " WHERE "
                + COLUMN_ID + "=" + id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            //удалить старую таблицу
            db.execSQL("DROP TABLE " + TABLE_COMPUTERS);
        } catch (Exception exception) {

        } finally {
            //создать новую и заполнить ее
            onCreate(db);
        }

    }
}