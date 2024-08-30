package com.example.notefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class NoteTakingFragment extends Fragment {
    private MyViewModel myViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.note_take_fragment, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        EditText editTitle = view.findViewById(R.id.editTitle);
        EditText editText = view.findViewById(R.id.editText);
        Button saveButton = view.findViewById(R.id.saveButton);

        // Populate EditTexts with existing data
        myViewModel.getNoteTitle().observe(getViewLifecycleOwner(), editTitle::setText);
        myViewModel.getNoteContent().observe(getViewLifecycleOwner(), editText::setText);

        // Save the note
        saveButton.setOnClickListener(v -> {
            myViewModel.setNoteTitle(editTitle.getText().toString());
            myViewModel.setNoteContent(editText.getText().toString());
            myViewModel.saveNote();
        });

        return view;
    }
}


