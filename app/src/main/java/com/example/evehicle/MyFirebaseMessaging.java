package com.example.evehicle;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    private int CHANNEL_ID= 101;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            createNotification(remoteMessage);
        }

    }
    private void createNotification(RemoteMessage payload){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(payload.getNotification().getTitle())
                .setContentText(payload.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(10, builder.build());

    }
}
