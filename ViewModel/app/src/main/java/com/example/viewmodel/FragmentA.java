package com.example.viewmodel;

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

public class FragmentA extends Fragment {
    private MyViewModel myViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        EditText editText = view.findViewById(R.id.editTextText);
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(v -> {
            String text = editText.getText().toString();
            myViewModel.setData(text);
            requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FragmentB())
                .addToBackStack(null)
                .commit();
        });
        return view;
    }
}
