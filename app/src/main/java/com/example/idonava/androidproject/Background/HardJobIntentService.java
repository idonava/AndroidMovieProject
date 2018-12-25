package com.example.idonava.androidproject.Background;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.idonava.androidproject.Background.BGServiceActivity;
import com.example.idonava.androidproject.R;


public class HardJobIntentService extends IntentService {

    private static final String TAG = "HardJobIntentService";
    private boolean isDestroyed;


    public HardJobIntentService() {
        super(TAG);
        System.out.println("HardJobIntentService - 0");
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        isDestroyed = false;
        System.out.println("HardJobIntentService - 1");

        showToast("starting_intent_service_msg");
        for (int i = 0; i <= 100 && !isDestroyed; i++) {
            SystemClock.sleep(100);
            Intent broadcastIntent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
            broadcastIntent.putExtra(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_VALUE_KEY, i);
            sendBroadcast(broadcastIntent);
        }
        showToast("finishing_intent_service_msg");
    }

    protected void showToast(final String msg) {
        Intent intent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        intent.putExtra(BGServiceActivity.BackgroundProgressReceiver.SERVICE_STATUS, msg);
        sendBroadcast(intent);
    }

    @Override public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }

}