package com.example.idonava.androidproject.DownloadService;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;

public class DownloadThread extends Thread {
    private final String imageUrl;
    private final DownloadCallBack downloadCallBack;
    private int progress = 0;
    private long lastUpdateTime;

    public DownloadThread(String url, DownloadCallBack downloadCallBack) {
        imageUrl = url;
        this.downloadCallBack = downloadCallBack;
    }
    @Override
    public void run() {
        File file = createFile();
        if (file == null) {
            downloadCallBack.onError("Can't create file");
            return;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;

        URL url = null;
        try {
            url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                downloadCallBack.onError("Server returned HTTP response code: "
                        + connection.getResponseCode() + " - " + connection.getResponseMessage());
            }
            int fileLength = connection.getContentLength();
            // Input stream (Downloading file)
            inputStream = new BufferedInputStream(url.openStream(), 8192);

            // Output stream (Saving file)
            fos = new FileOutputStream(file.getPath());
            int next;
            byte[] data = new byte[1024];
            while ((next = inputStream.read(data)) != -1) {
                fos.write(data, 0, next);
                updateProgress(fos, fileLength);
            }

            downloadCallBack.onDownloadFinished(file.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void updateProgress(FileOutputStream fos, int fileLength) throws IOException {
        if (lastUpdateTime == 0 || System.currentTimeMillis() > lastUpdateTime + 500) {
            int count = ((int) fos.getChannel().size()) * 100 / fileLength;
            if (count > progress) {
                progress = count;
                lastUpdateTime = System.currentTimeMillis();
                downloadCallBack.onProgressUpdate(progress);
            }
        }
    }

    private File createFile() {
        File mediaStorageDirectory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String imageName = createImageFileName() + ".jpg";
        return new File(mediaStorageDirectory.getPath() + File.separator + imageName);
    }
    @NonNull
    private String createImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        return "FILE_" + timeStamp;
    }


    public interface DownloadCallBack {
        void onProgressUpdate(int percent);
        void onDownloadFinished(String filePath);
        void onError(String error);
    }

}
