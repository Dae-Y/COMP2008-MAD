package com.example.tute02calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText firstNumber = findViewById(R.id.firstNumber);
        EditText secondNumber = findViewById(R.id.secondNumber);
        TextView result = findViewById(R.id.resultView);

        // basic +, -, *, / buttons
        Button addButton = findViewById(R.id.addButton);
        Button minusButton = findViewById(R.id.subButton);
        Button multiButton = findViewById(R.id.mulButton);
        Button divButton = findViewById(R.id.divButton);

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNumStr = firstNumber.getText().toString();
                String secondNumStr = secondNumber.getText().toString();

                // check whether input numbers empty or not
                if (firstNumStr.isEmpty() || secondNumStr.isEmpty()) {
                    result.setText("Please enter both numbers");
                    return;
                }

                try {

                    // parse the input numbers as doubles
                    double i = Double.parseDouble(firstNumber.getText().toString());
                    double j = Double.parseDouble(secondNumber.getText().toString());
                    double resultValue = 0;

                    // determine which button was clicked and perform the corresponding operation
                    if (view.getId() == R.id.addButton) {
                        resultValue = i + j;
                    } else if (view.getId() == R.id.subButton) {
                        resultValue = i - j;
                    } else if (view.getId() == R.id.mulButton) {
                        resultValue = i * j;
                    } else if (view.getId() == R.id.divButton) {
                        if (j != 0) {
                            resultValue = i / j;
                        } else {
                            result.setText("Division by Zero Error");
                            return;
                        }
                    }

                    // display the result
                    result.setText(String.valueOf(resultValue));

                } catch (NumberFormatException e)
                {
                    result.setText("Please enter numbers only");
                }
            }
        };
        // attach the same listener to all the buttons
        addButton.setOnClickListener(operationListener);
        minusButton.setOnClickListener(operationListener);
        multiButton.setOnClickListener(operationListener);
        divButton.setOnClickListener(operationListener);

    }
}

