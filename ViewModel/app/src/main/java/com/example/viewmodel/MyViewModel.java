package com.example.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private final MutableLiveData<String> data = new MutableLiveData<>();
    // Method to get the data
    public LiveData<String> getData() {
        return data;
    }
    // Method to update the data
    public void setData(String value) {
        data.setValue(value);
    }
}
