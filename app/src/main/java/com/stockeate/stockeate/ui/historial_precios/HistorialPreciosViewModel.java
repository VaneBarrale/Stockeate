package com.stockeate.stockeate.ui.historial_precios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistorialPreciosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistorialPreciosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}