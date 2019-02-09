package com.example.idonava.androidproject.DownloadService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import com.example.idonava.androidproject.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class DownloadService extends Service {

    public static final int ONGOING_NOTIFICATION_ID = 14000605;
    private static final String CHANNEL_DEFAULT_IMPORTANCE = "Channel";
    public static final String BROADCAST_ACTION = "com.academy.fundamentals.DOWNLOAD_COMPLETE";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public static final String URL = "URL";
    public static void startService(Activity activity, String url) {
        System.out.println(url);
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(URL, url);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        startForeground();
        String url = intent.getStringExtra(URL);

        startDownloadThread(url);
        return START_STICKY;
    }

    private void startDownloadThread(String url) {
        new DownloadThread(url, new DownloadThread.DownloadCallBack() {
            @Override
            public void onProgressUpdate(int percent) {
                updateNotification(percent);
            }
            @Override
            public void onDownloadFinished(String filePath) {
                sendBroadcastMsgDownloadComplete(filePath);
                stopSelf();

            }
            @Override
            public void onError(String error) {

            }
        }).start();

    }
    private void sendBroadcastMsgDownloadComplete(String filePath) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(DownloadActivity.ARG_FILE_PATH, filePath);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startForeground() {
        createNotificationChannel();
        Notification notification = createNotification(0);
        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }
    private void updateNotification(int progress) {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            Notification notification = createNotification(progress);
            notificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
        }
    }

    private Notification createNotification(int progress) {
        Intent notificationIntent = new Intent(this, DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        String progressStr = getString(R.string.notification_message, progress);

        return new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(progressStr)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setProgress(100, progress, false)
                .setContentIntent(pendingIntent)
                .build();
    }


    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name); // The user-visible name
        String description = getString(R.string.channel_description); // The user-visible description
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);

        }
        else{
            Log.e("[ido]","android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O");
        }

    }

}
