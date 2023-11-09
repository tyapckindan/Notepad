package com.example.notepad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final public static String KEY_COMPUTER = "computer";
    final public static String KEY_STATUS = "status";
    final public static String KEY_LOCATION = "location";
    final public static String KEY_TIME = "time";
    final public static String KEY_POSITION = "position";
    ListView ComputerListView;
    SimpleCursorAdapter computersAdapter;
    DataBaseAccessor db;

    // создание launcher для получения данных из дочерней активити
    ActivityResultLauncher<Intent> ComputersLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // все ли хорошо при получении данных из дочерней активити?
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        //получить данные
                        Intent returnedIntent = result.getData();
                        int id = returnedIntent.getIntExtra(KEY_POSITION,-1);
                        String computer = returnedIntent.getStringExtra(KEY_COMPUTER);
                        String status = returnedIntent.getStringExtra(KEY_STATUS);
                        String location = returnedIntent.getStringExtra(KEY_LOCATION);
                        String time = returnedIntent.getStringExtra(KEY_TIME);

                        // обновить БД и интерфейс
                        db.updateComputers(id,computer,status, location, time);
                        computersAdapter = AdapterUpdate();
                    }
                    else
                    {
                        Log.d("MainActivity" ,"Invalid note activity result");
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создать аксессор к бд
        db = new DataBaseAccessor(this);

        setContentView(R.layout.activity_main);
        ComputerListView = findViewById(R.id.list);

        computersAdapter = AdapterUpdate();
        Intent ComputersIntent = new Intent(this, SelectItemActivity.class);

        // Обработка нажатия по элементу списка
        ComputerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Добыть данные из адаптера
                String computer = ((Cursor) computersAdapter.getItem(position)).getString(1);
                String status = ((Cursor) computersAdapter.getItem(position)).getString(2);
                String location = ((Cursor) computersAdapter.getItem(position)).getString(3);
                String time = ((Cursor) computersAdapter.getItem(position)).getString(4);
                //отправить данные в дочернюю активити
                ComputersIntent.putExtra(KEY_COMPUTER, computer);
                ComputersIntent.putExtra(KEY_STATUS, status);
                ComputersIntent.putExtra(KEY_LOCATION, location);
                ComputersIntent.putExtra(KEY_TIME, time);

                //id - идентификатор записи в БД
                //без приведения к int придется и получать long
                ComputersIntent.putExtra(KEY_POSITION, (int)id);
                //запустить дочернюю активити
                ComputersLauncher.launch(ComputersIntent);
            }
        });
    }

        /**
         * Обновляет listView путем установки нового адаптера
         * @return Адаптер для обновления listView
         */
        private SimpleCursorAdapter AdapterUpdate() {
            // получить адаптер из класса
            SimpleCursorAdapter adapter = db.getCursorAdapter(this,
                    android.R.layout.two_line_list_item, // Разметка одного элемента ListView
                    new int[]{android.R.id.text1,android.R.id.text2}); // текст этого элемента

            // установить адаптер в listview
            ComputerListView.setAdapter(adapter);
            return adapter;
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // закрыть БД
        db.close();
    }
}