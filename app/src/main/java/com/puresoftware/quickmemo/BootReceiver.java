package com.puresoftware.quickmemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.puresoftware.quickmemo.floating.WidgetService;

public class BootReceiver extends BroadcastReceiver {

    String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent tempService = new Intent(context, WidgetService.class);
            SharedPreferences pref = context.getSharedPreferences("floating_status", Context.MODE_PRIVATE);
            String status = pref.getString("status", "none");
            String mode = pref.getString("mode", "none");
            Log.i(TAG, "status:" + status + "/mode:" + mode);

            if (status.equals("true")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(tempService);
                } else {
                    context.startService(tempService);
                }
            } else {
            }
        }

//        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
//            Intent tempService = new Intent(context, WidgetService.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(tempService);
//            } else {
//                context.startService(tempService);
//            }
//        }
    }
}
