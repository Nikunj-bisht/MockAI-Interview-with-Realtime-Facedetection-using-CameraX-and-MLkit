package com.animesafar.dinterviewkit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notibroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

         if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

             NotificationChannel notificationChannel = new NotificationChannel("channel1","Channel 1", NotificationManager.IMPORTANCE_HIGH);

              notificationChannel.setDescription("Get ready to prepare for interview!");

                 NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

                 notificationManager.createNotificationChannel(notificationChannel);

             NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

             Notification notification = new NotificationCompat.Builder(context,"channel1")
                     .setSmallIcon(R.drawable.ic_baseline_query_builder_24)
                     .setContentTitle("Hey!")
                     .setContentInfo("Get ready to prepare for interview!")
                     .setPriority(NotificationCompat.PRIORITY_HIGH)
                     .build();

             notificationManagerCompat.notify(1,notification);

         }

    }
}
