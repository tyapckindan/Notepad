package com.example.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SelectItemActivity extends AppCompatActivity {
    private static final String PREF_TEXT = "Text"; // Переменная для хранения текста в настройках
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;
    EditText textText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        textText = findViewById(R.id.text);
        settings = getSharedPreferences(PREF_TEXT, MODE_PRIVATE);

        // Получаем настройки
        String text = settings.getString(PREF_TEXT,"");
        textText.setText(text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Получаем заголовок
            EditText nameText = findViewById(R.id.name);
            String named = extras.getString((MainActivity.BOOKMARK_KEY));
            nameText.setText(named);
        }

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(view -> {

            EditText nameText = findViewById(R.id.name);

            String named = nameText.getText().toString();

            Intent data = new Intent();
            data.putExtra(MainActivity.BOOKMARK_KEY,named);
            setResult(RESULT_OK, data);
            finish();
        });
    }

    @Override
    protected void onPause(){
        super.onPause();

        String text = textText.getText().toString();
        // Сохраняем в настройках
        prefEditor = settings.edit();
        prefEditor.putString(PREF_TEXT, text);
        prefEditor.apply();
    }
}