package com.example.notefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.content.res.Configuration;

/*
public class MenuFragment extends Fragment {
    private MyViewModel myViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        Button button = view.findViewById(R.id.addNewButton);

        myViewModel.getNoteTitle().observe(getViewLifecycleOwner(), title -> {
            if (!title.isEmpty()) {
                button.setText("Edit Note");
                // Check if the current orientation is landscape
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // Disable button click in landscape mode
                    button.setEnabled(false);
                } else {
                    // Enable button click in portrait mode
                    button.setEnabled(true);
                }
            } else {
                button.setText("Add New Note");
                button.setEnabled(true);
            }
        });

        button.setOnClickListener(v -> {
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoteTakingFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
*/

public class MenuFragment extends Fragment {
    private MyViewModel myViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        Button button = view.findViewById(R.id.addNewButton);

        // Initialize button text and visibility
        updateButtonState(button);

        // Observe the note title to update the button text and enable/disable it
        myViewModel.getNoteTitle().observe(getViewLifecycleOwner(), title -> {
            updateButtonState(button);
        });

        button.setOnClickListener(v -> {
            // Handle button click based on orientation
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Do nothing in landscape mode
            } else {
                // In portrait mode, replace the fragment and add to back stack
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoteTakingFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void updateButtonState(Button button) {
        String noteTitle = myViewModel.getNoteTitle().getValue();
        if (noteTitle == null || noteTitle.isEmpty()) {
            button.setText("Add New Note");
        } else {
            button.setText("Edit Note");
        }

        // Always show the button in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            button.setEnabled(true);
            button.setVisibility(View.VISIBLE); // Ensure button is visible
        } else {
            button.setVisibility(View.VISIBLE); // Ensure button is visible in portrait mode as well
        }
    }
}






