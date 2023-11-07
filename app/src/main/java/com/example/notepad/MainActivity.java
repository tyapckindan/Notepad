package com.example.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final public static String KEY_COMPUTER = "computer";
    final public static String KEY_POSITION = "position";
    ListView ComputerListView;
    DataBaseAccessor db;
    ArrayList<String> computersList;
    ArrayAdapter<String> adapter;

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

        setContentView(R.layout.activity_main);

        ComputerListView = findViewById(R.id.list);

        computersList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, computersList); // Адаптер для списка компьютеров
        ComputerListView.setAdapter(adapter);// адаптер для ListView

        // Обработка нажатия по элементу списка
        ComputerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // закрыть БД
        db.close();
    }
}