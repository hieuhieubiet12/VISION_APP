package com.example.vision_app.Notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.vision_app.R;


public class SetNotification {
    private static final int NOTIFICATION_ID = 1;
    Context context;

    public SetNotification(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public void sendNotification(String title,String noiDung) {
        // Tạo kênh thông báo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_person_24);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setContentTitle(title)
                .setContentText(noiDung)
                .setSmallIcon(R.drawable.baseline_person_24)
                .setLargeIcon(largeIcon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
