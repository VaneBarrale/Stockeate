package com.stockeate.stockeate.ui.escanear_codigos_barra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModel_escanear_codigos_barra extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewModel_escanear_codigos_barra() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}