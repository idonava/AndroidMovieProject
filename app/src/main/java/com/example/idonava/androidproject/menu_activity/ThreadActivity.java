package com.example.idonava.androidproject.menu_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.idonava.androidproject.R;

public class ThreadActivity extends AppCompatActivity implements IAsyncTaskEvents {

    private static FragmentManager mFragmentManager;
    private CounterFragment mThreadsFragment;
    private MySimpleAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        mFragmentManager = getSupportFragmentManager();//Get Fragment Manager
        if (mThreadsFragment == null) {
            mThreadsFragment = CounterFragment.newInstance(getString(R.string.thread_activity));
            mFragmentManager.beginTransaction().replace(R.id.fragment, mThreadsFragment).commit();//now replace the argument fragment
        }
    }

    @Override
    public void createAsyncTask() {
        mAsyncTask = new MySimpleAsyncTask(this);

    }

    @Override
    public void startAsyncTask() {
        if (mAsyncTask != null && !mAsyncTask.isCanceled()) {
            mAsyncTask.execute();
        }
    }

    @Override
    public void cancelAsyncTask() {
        if (mAsyncTask.isCanceled()) {
            mAsyncTask.cancel();
        }
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText(getString(R.string.done));
        mAsyncTask = null;

    }

    @Override
    public void onProgressUpdate(Integer integer) {
        mThreadsFragment.updateFragmentText(String.valueOf(integer));

    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.msg_oncancel), Toast.LENGTH_SHORT).show();

    }
}
