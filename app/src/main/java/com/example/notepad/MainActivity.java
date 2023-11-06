package com.example.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static String BOOKMARK_KEY = "BOOKMARK"; // Заголовок
    String name = ""; // Для хранения заголовка

    private final String[] Topics = {"Тема 1", "Тема 2", "Тема 3"};

    // Получение данных работы активити
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                TextView textView = findViewById(R.id.text_item);
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();
                    assert intent != null;
                    String bookmark = intent.getStringExtra("BOOKMARK");
                    textView.setText(bookmark);
                    name = bookmark;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Topics);
        list.setAdapter(adapter);// адаптер для ListView

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String topic = Topics[position];
                TextView textView = findViewById(R.id.text_item);
                String bookmark = textView.getText().toString();

                Intent intent = new Intent(MainActivity.this, SelectItemActivity.class);
                intent.putExtra(BOOKMARK_KEY, bookmark);
                intent.putExtra("topic", topic);

                mStartForResult.launch(intent);
            }
        });
    }

    // Переход на другую активити, передача заголовка
    public void TextViewOnClick(View view) {


    }

    // Сохраняем заголовок
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bookmark", name);
    }

    //Возвращаем заголовок
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null &&
                savedInstanceState.containsKey("bookmark")) {
            name = savedInstanceState.getString("bookmark");
            TextView counterView = findViewById(R.id.text_item);
            counterView.setText(name);
        }
    }
}
