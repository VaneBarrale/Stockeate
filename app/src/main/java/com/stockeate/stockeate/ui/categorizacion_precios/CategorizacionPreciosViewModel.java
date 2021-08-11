package com.stockeate.stockeate.ui.categorizacion_precios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategorizacionPreciosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategorizacionPreciosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}