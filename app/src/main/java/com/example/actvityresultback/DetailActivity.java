package com.example.actvityresultback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        EditText editText = findViewById(R.id.editText);
        Button backButton = findViewById(R.id.backButtonDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                intent.putExtra("NAME",editText.getText().toString());
                setResult(RESULT_OK,intent); //you can send other int as well
                finish();
            }
        });
    }
}

