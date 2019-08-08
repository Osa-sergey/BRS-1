package com.misis.brs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    private final static String CHANNEL_ID = "channel1";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_bn_tasks)
                .setContentTitle("BRS Deadline is comming")
                .setChannelId(CHANNEL_ID)
                .setContentText("");

            builder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
            builder.setCategory(Notification.CATEGORY_REMINDER);




                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channel 1", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("BRS deadline is comming");




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
             notificationManager.createNotificationChannel(channel);

        notificationManager.notify(1, builder.build());
             }

    }
}
