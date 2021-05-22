package com.stockeate.stockeate.ui.detalle_lista_precios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModel_Detalle_Lista_Precios extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewModel_Detalle_Lista_Precios() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}