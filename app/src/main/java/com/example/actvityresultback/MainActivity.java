package com.example.actvityresultback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addNewButton, note01Button, note02Button, note03Button, note04Button;
    List<String> notes = new ArrayList<>();
    ActivityResultLauncher<Intent> detailActivityLauncher;
    ActivityResultLauncher<Intent> printActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewButton = findViewById(R.id.addNewButton);
        note01Button = findViewById(R.id.note01Button);
        note02Button = findViewById(R.id.note02Button);
        note03Button = findViewById(R.id.note03Button);
        note04Button = findViewById(R.id.note04Button);

        // Initialize ActivityResultLauncher for DetailActivity
        detailActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String note = intent.getStringExtra("NAME");
                            if (note != null) {
                                notes.add(note);
                                updateNoteButtons();
                            }
                        }
                    }
                }
        );

        // Initialize ActivityResultLauncher for PrintActivity
        printActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String updatedNote = intent.getStringExtra("NAME");
                            if (updatedNote != null) {
                                // Find the index of the note and update it
                                int index = intent.getIntExtra("NOTE_INDEX", -1);
                                if (index >= 0 && index < notes.size()) {
                                    notes.set(index, updatedNote);
                                    updateNoteButtons();
                                }
                            }
                        }
                    }
                }
        );

        // Add a new note
        addNewButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            detailActivityLauncher.launch(intent);
        });

        // Note buttons click listeners
        note01Button.setOnClickListener(view -> openPrintActivity(0));
        note02Button.setOnClickListener(view -> openPrintActivity(1));
        note03Button.setOnClickListener(view -> openPrintActivity(2));
        note04Button.setOnClickListener(view -> openPrintActivity(3));

        updateNoteButtons();
    }

    private void updateNoteButtons() {
        // Hide all buttons
        note01Button.setVisibility(View.GONE);
        note02Button.setVisibility(View.GONE);
        note03Button.setVisibility(View.GONE);
        note04Button.setVisibility(View.GONE);

        // Show buttons based on the number of notes
        if (notes.size() > 0) note01Button.setVisibility(View.VISIBLE);
        if (notes.size() > 1) note02Button.setVisibility(View.VISIBLE);
        if (notes.size() > 2) note03Button.setVisibility(View.VISIBLE);
        if (notes.size() > 3) note04Button.setVisibility(View.VISIBLE);
    }

    private void openPrintActivity(int index) {
        Intent intent = new Intent(MainActivity.this, PrintActivity.class);
        if (index >= 0 && index < notes.size()) {
            intent.putExtra("NAME", notes.get(index));
            intent.putExtra("NOTE_INDEX", index); // Pass the note index
            printActivityLauncher.launch(intent);
        }
    }
}