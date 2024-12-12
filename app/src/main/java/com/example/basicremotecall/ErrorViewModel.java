package com.example.basicremotecall;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ErrorViewModel extends ViewModel {
    public MutableLiveData<Integer> errorCode;
    public ErrorViewModel(){
        errorCode = new MutableLiveData<Integer>();
        errorCode.setValue(0);
    }

    public Integer getErrorCode(){
        return errorCode.getValue();
    }
    public void setErrorCode(Integer value){
        errorCode.postValue(value);
    }
}
