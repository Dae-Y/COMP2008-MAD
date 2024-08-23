package com.example.actvityresultback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        EditText editText = findViewById(R.id.editText);
        Button saveButton = findViewById(R.id.saveButton);
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        int noteIndex = intent.getIntExtra("NOTE_INDEX", -1);
        editText.setText(name);
        saveButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NAME", editText.getText().toString());
            resultIntent.putExtra("NOTE_INDEX", noteIndex);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}