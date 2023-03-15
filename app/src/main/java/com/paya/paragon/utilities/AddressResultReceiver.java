package com.paya.paragon.utilities;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.paya.paragon.base.commonClass.GlobalValues;

public class AddressResultReceiver extends ResultReceiver {
    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (resultData == null) {
            return;
        }

        // Display the address string 
        // or an error message sent from the intent service. 
        String mAddressOutput = resultData.getString(GlobalValues.RESULT_DATA_KEY);
        if (mAddressOutput == null || mAddressOutput.equalsIgnoreCase("No Address Found")) {
            mAddressOutput = "ernakulam";
        }
        GlobalValues.address = mAddressOutput;
        //displayAddressOutput();

        // Show a toast message if an address was found. 
        if (resultCode == GlobalValues.SUCCESS_RESULT) {
            // Toast.makeText(getString(R.string.address_found));
        }

    }
} 

