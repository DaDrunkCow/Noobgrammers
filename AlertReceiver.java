package com.yorku.noobgrammers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver
{
    private static final String TAG = "AlertReceiver";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "onReceive: s00ka");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}
