package com.paya.paragon.push;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.paya.paragon.PayaAppClass;
import com.paya.paragon.R;
import com.paya.paragon.activity.intro.ActivitySplashScreen;
import com.paya.paragon.utilities.SessionManager;

import org.jetbrains.annotations.NotNull;

public class PayaFirebaseMessagingService extends FirebaseMessagingService {

    private final int APP_NOTIFICATION_ID = 0;
    private final String CHANNEL_ID = "Default";
    private final String CHANNEL_NAME = "Default channel";

    @Override
    public void onNewToken(@NonNull @NotNull String token) {
        SessionManager.setDeviceTokenForFCM(PayaAppClass.getAppInstance(), token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        handleIncomingData(remoteMessage);
    }

    private void handleIncomingData(RemoteMessage remoteMessage) {
        if (remoteMessage != null) {
            String title = getApplicationContext() != null ? getApplicationContext().getString(R.string.text_paya_notification_title) : "PAYA Notification";
            String body = getApplicationContext() != null ? getApplicationContext().getString(R.string.text_paya_notification_body) : "Received new Notification";
            String clickAction = "";
            String type = "";
            String id = "";


            if (remoteMessage.getNotification() != null) {
                title = remoteMessage.getNotification().getTitle();
                body = remoteMessage.getNotification().getBody();
            }
            if (remoteMessage.getData().size() > 0) {
                clickAction = remoteMessage.getData().get("propertyUrl");
                type = remoteMessage.getData().get("type");
                id = remoteMessage.getData().get("type_id");
            }


            Intent intent = new Intent(this, ActivitySplashScreen.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("type", type);
            intent.putExtra("type_id", id);
            intent.setData(Uri.parse(clickAction));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_paya_logo)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);


            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(APP_NOTIFICATION_ID, builder.build());
        }
    }
}
