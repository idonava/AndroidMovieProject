package com.example.idonava.androidproject.Background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idonava.androidproject.R;

import java.util.Locale;

public class BGServiceActivity extends AppCompatActivity {
    TextView precentTextView;
    Button intentServiceButton;
    Button serviceButton;
    public static final String PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION";
    public static final String PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY";
    public static final String SERVICE_STATUS = "SERVICE_STATUS";

    private BackgroundProgressReceiver mBackgroundProgressReceiver;
    private boolean mIsServiceStarted;
    private boolean mIsIntentServiceStarted;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgservice);
        precentTextView = findViewById(R.id.precent_tv);
        intentServiceButton = findViewById(R.id.start_intent_service_button);
        serviceButton = findViewById(R.id.start_service_button);
    }

    public void onResume() {
        super.onResume();
        subscribeForProgressUpdates();

    }

    @Override
    protected void onPause() {
        if (mBackgroundProgressReceiver != null) {
            unregisterReceiver(mBackgroundProgressReceiver);
        }
        super.onPause();
    }

    private void subscribeForProgressUpdates() {
        if (mBackgroundProgressReceiver == null) {
            mBackgroundProgressReceiver = new BackgroundProgressReceiver();
        }
        IntentFilter progressUpdateActionFilter = new IntentFilter(PROGRESS_UPDATE_ACTION);
        registerReceiver(mBackgroundProgressReceiver, progressUpdateActionFilter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_intent_service_button:
                System.out.println("0");
                if (mIsServiceStarted) {
                    stopService(new Intent(this, HardJobService.class));
                    mIsServiceStarted = false;
                    System.out.println("1");
                }
                if (!mIsIntentServiceStarted) {
                    mIsIntentServiceStarted = true;
                    System.out.println("2");

                    startService(new Intent(this, HardJobIntentService.class));
                }
                break;
            case R.id.start_service_button:
                if (mIsIntentServiceStarted) {
                    stopService(new Intent(this, HardJobIntentService.class));
                    mIsIntentServiceStarted = false;
                }
                if (!mIsServiceStarted) {
                    mIsServiceStarted = true;
                    startService(new Intent(this, HardJobService.class));
                }
                break;

        }


    }

    public class BackgroundProgressReceiver extends BroadcastReceiver {
        public static final String PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION";
        public static final String PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY";
        public static final String SERVICE_STATUS = "SERVICE_STATUS";

        @Override
        public void onReceive(Context context, Intent intent) {

            int progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1);
            if (progress >= 0) {
                String text;
                if (progress == 100) {
                    text = "Done!";
                    mIsIntentServiceStarted = false;
                    mIsServiceStarted = false;
                } else {
                    text = String.format(Locale.getDefault(), "%d%%", progress);
                }
                precentTextView.setText(text);
            }

            String msg = intent.getStringExtra(SERVICE_STATUS);
            if (msg != null) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                mToast.show();
            }

        }
    }

    @Override
    public void onDestroy() {
        if (isFinishing()) {
            if (mIsIntentServiceStarted) {
                stopService(new Intent(this, HardJobIntentService.class));
            }
            if (mIsServiceStarted) {
                stopService(new Intent(this, HardJobService.class));
            }
            if (mToast != null) {
                mToast.cancel();
            }
        }
        super.onDestroy();
    }


}
