package com.puresoftware.quickmemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.puresoftware.quickmemo.floating.WidgetService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
//            Intent tempService = new Intent(context, WidgetService.class);
//            SharedPreferences pref = context.getSharedPreferences("floating_status", Context.MODE_PRIVATE);
//            String status = pref.getString("status", "false");
//            String mode = pref.getString("mode", "none");
//
//            if (status.equals("true")) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    context.startForegroundService(tempService);
//                } else {
//                    context.startService(tempService);
//                }
//            } else {
//            }
//        }

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent tempService = new Intent(context, WidgetService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(tempService);
            } else {
                context.startService(tempService);
            }
        }
    }
}
