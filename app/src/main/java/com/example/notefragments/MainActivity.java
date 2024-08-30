package com.example.notefragments;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import android.content.res.Configuration;


public class MainActivity extends AppCompatActivity {

    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        if (savedInstanceState == null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menu_fragment_container, new MenuFragment())
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.note_fragment_container, new NoteTakingFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MenuFragment())
                        .commit();
            }
        }

        myViewModel.isNoteSaved().observe(this, isSaved -> {
            if (isSaved) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.menu_fragment_container, new MenuFragment())
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.note_fragment_container, new NoteTakingFragment())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MenuFragment())
                            .commit();
                }
            }
        });
    }


} // END MainActivity







