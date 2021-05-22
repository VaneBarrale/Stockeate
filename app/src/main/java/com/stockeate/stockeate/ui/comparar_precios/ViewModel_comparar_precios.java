package com.stockeate.stockeate.ui.comparar_precios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModel_comparar_precios extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewModel_comparar_precios() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}