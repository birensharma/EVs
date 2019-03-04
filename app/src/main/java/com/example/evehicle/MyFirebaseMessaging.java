package com.example.evehicle;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    private String CHANNEL_ID= "201";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            if(!remoteMessage.getData().get("email").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                createNotification(remoteMessage);
        }

    }
    private void createNotification(RemoteMessage payload){
        long[] pattern={500,500,500,500,500};
        int icon =R.drawable.electriccar;
        Intent resultIntent = new Intent(this, Response.class);
        TaskStackBuilder stackBuilder = null;
        PendingIntent resultPendingIntent=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = getString(R.string.channel_name);
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(icon)
                        .setBadgeIconType(icon)
                        .setContentTitle(payload.getNotification().getTitle())
                        .setContentText(payload.getNotification().getBody())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(pattern)
                        .setLights(Color.WHITE,500,500)
                        .setContentIntent(resultPendingIntent);
                notificationManager.notify(10, builder.build());
            }
            else {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(icon)
                        .setBadgeIconType(icon)
                        .setContentTitle(payload.getNotification().getTitle())
                        .setContentText(payload.getNotification().getBody())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(pattern)
                        .setLights(Color.WHITE,500,500)
                        .setContentIntent(resultPendingIntent);
                notificationManager.notify(10, builder.build());
            }


    }
}
