package com.example.notefragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private final MutableLiveData<String> noteTitle = new MutableLiveData<>("");
    private final MutableLiveData<String> noteContent = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isNoteSaved = new MutableLiveData<>(false);

    public LiveData<String> getNoteTitle() {
        return noteTitle;
    }

    public LiveData<String> getNoteContent() {
        return noteContent;
    }

    public LiveData<Boolean> isNoteSaved() {
        return isNoteSaved;
    }

    public void setNoteTitle(String title) {
        noteTitle.setValue(title);
    }

    public void setNoteContent(String content) {
        noteContent.setValue(content);
    }

    public void saveNote() {
        isNoteSaved.setValue(true);
    }
}

