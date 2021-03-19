package com.xxun.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import com.xxun.xunintegral.Canstance;
import android.content.Intent;
import android.util.Log;

public class CleanProvider extends BroadcastReceiver {
    public static final String TAG = "CleanProvider";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "CleanProvider action: "+ action);
//        Uri uri_user = Uri.parse("content://" + Canstance.AUTOHORITY + "/intergral");
//        context.getContentResolver().delete(uri_user, null, null);
    }

}