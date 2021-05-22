package com.stockeate.stockeate.ui.agregar_promocion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModel_agregar_promocion extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewModel_agregar_promocion() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}