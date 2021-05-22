package com.stockeate.stockeate.ui.promocion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PromocionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PromocionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is promotion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}