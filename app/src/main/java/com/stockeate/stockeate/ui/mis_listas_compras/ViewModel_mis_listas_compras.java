package com.stockeate.stockeate.ui.mis_listas_compras;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModel_mis_listas_compras extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewModel_mis_listas_compras() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}