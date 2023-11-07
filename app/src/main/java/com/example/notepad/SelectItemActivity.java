package com.example.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SelectItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        // Кнопка назад
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}