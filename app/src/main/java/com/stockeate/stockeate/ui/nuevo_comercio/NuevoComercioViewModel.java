package com.stockeate.stockeate.ui.nuevo_comercio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NuevoComercioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NuevoComercioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is nuevo comercio fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}