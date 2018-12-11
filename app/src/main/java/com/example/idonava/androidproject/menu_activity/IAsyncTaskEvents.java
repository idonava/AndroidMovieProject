package com.example.idonava.androidproject.menu_activity;

public interface IAsyncTaskEvents {
    void createAsyncTask();
    void startAsyncTask();
    void cancelAsyncTask();

    void onPreExecute() throws InterruptedException;
    void onPostExecute();
    void onProgressUpdate(Integer integer);
    void onCancel();

}

