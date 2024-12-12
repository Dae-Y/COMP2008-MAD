package com.example.basicremotecall;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchResponseViewModel extends ViewModel {

    public MutableLiveData<String> response;
    public SearchResponseViewModel(){
        response = new MutableLiveData<String>();
    }

    public String getResponse(){
        return response.getValue();
    }
    public void setResponse(String value){
        response.postValue(value);
    }
}
