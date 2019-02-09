package com.example.idonava.androidproject.DownloadService;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.idonava.androidproject.MovieModel;
import com.example.idonava.androidproject.R;

public class DownloadActivity extends AppCompatActivity {
    public static final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int PERMISSIONS_REQUEST_CODE = 42;
    public static final String ARG_FILE_PATH = "Image-File-Path";
    private static final String ARG_MOVIE_MODEL = "Movie-Model-Data";
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String filePath = intent.getStringExtra(ARG_FILE_PATH);
                Log.d("TAG", "DownloadActivity # onReceive, filePath: " + filePath);
                if (!TextUtils.isEmpty(filePath)) {
                    showImage(filePath);
                }
            }
        };

        if (isPermissionGranted()){
            startDownloadService();
        }
        else{
            requestPermissions();
        }
    }
    private void showImage(String filePath) {
        ImageView imageView = findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bitmap);
    }

    public static void startActivity(Context context, MovieModel movieModel){
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.putExtra(ARG_MOVIE_MODEL, movieModel);
        context.startActivity(intent);

}

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)) {
            showExplainingRationaleDialog(); // next slide
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{PERMISSION}, PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestWritePermission() {
        // PERMISSIONS_REQUEST_CODE is an app-defined int constant.
        // The callback method gets the result of the request.
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, PERMISSIONS_REQUEST_CODE);
    }
    private void showExplainingRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.download_dialog_title);
        builder.setMessage(R.string.download_dialog_message);
        builder.setPositiveButton(R.string.download_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestWritePermission();
            }
        });
        builder.setNegativeButton(R.string.download_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // no Permission - finish activity
                finishActivity();
            }
        });
        builder.create().show();
    }


    private boolean isPermissionGranted(){
        return ContextCompat.checkSelfPermission(this, PERMISSION)
                == PackageManager.PERMISSION_GRANTED;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay!
                startDownloadService();
            } else {
                // permission denied, boo! Disable the functionality that depends on this permission.
                finishActivity();
            }
        }
    }

    private void finishActivity() {
        this.finish();
    }

    private void startDownloadService() {
        MovieModel movieModel = getIntent().getParcelableExtra(ARG_MOVIE_MODEL);
        Log.d("TAG", "DownloadActivity # onCreate, movieModel: " + movieModel);
        if (movieModel == null) return;

        Log.d("TAG", "DownloadActivity # onRequestPermissionsResult, startDownloadService");
        DownloadService.startService(this, movieModel.getPosterPathUri());
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }


}
