package com.paya.paragon.base;

public interface Interactor {

    void initiateAPICall(int apiTransactionID);

    void onAPISuccess(String response, int apiTransactionID);

    void onAPIFailure(String response, int apiTransactionID);
}
