package com.example.basicremotecall;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImageViewModel  extends ViewModel {
    public MutableLiveData<Bitmap> image;
    public ImageViewModel(){
        image = new MutableLiveData<Bitmap>();
    }

    public Bitmap getImage(){
        return image.getValue();
    }
    public void setImage(Bitmap bitmap){
        image.postValue(bitmap);
    }
}
