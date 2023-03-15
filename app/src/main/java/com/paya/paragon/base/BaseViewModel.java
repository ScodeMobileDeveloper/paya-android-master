package com.paya.paragon.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel implements Interactor {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initiateAPICall(int apiTransactionID) {

    }

    @Override
    public void onAPISuccess(String response, int apiTransactionID) {

    }

    @Override
    public void onAPIFailure(String response, int apiTransactionID) {

    }
}
