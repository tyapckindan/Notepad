package com.example.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SelectItemActivity extends AppCompatActivity {

    EditText NameComputerEditText;
    EditText StatusEditText;
    EditText LocationEditText;
    EditText TimeEditText;

    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        NameComputerEditText = findViewById(R.id.name);
        StatusEditText = findViewById(R.id.status);
        LocationEditText = findViewById(R.id.location);
        TimeEditText = findViewById(R.id.timeExitNet);

        Intent fromMainActivityIntent = getIntent();

        NameComputerEditText.setText(fromMainActivityIntent.getExtras().getString(MainActivity.KEY_COMPUTER));
        StatusEditText.setText(fromMainActivityIntent.getExtras().getString(MainActivity.KEY_STATUS));
        LocationEditText.setText(fromMainActivityIntent.getExtras().getString(MainActivity.KEY_LOCATION));
        TimeEditText.setText(fromMainActivityIntent.getExtras().getString(MainActivity.KEY_TIME));
        Position = fromMainActivityIntent.getIntExtra(MainActivity.KEY_POSITION,-1);

        if(Position == -1)
        {
            Log.d("SelectItem activity","Invalid position");
        }

        // Кнопка назад
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            setResult(RESULT_OK,returnIntent);
            finish();
        });
    }
}