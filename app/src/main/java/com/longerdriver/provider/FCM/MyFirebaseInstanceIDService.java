package com.longerdriver.provider.FCM;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.longerdriver.provider.Helper.SharedHelper;
import com.longerdriver.provider.Utilities.Utilities;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    Utilities utils = new Utilities();
    Context context;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedHelper.putKey(context.getApplicationContext(),"device_token",""+refreshedToken);
        Log.d(TAG, "onTokenRefresh: ");
        utils.print(TAG, "onTokenRefresh" + refreshedToken);
    }
}
