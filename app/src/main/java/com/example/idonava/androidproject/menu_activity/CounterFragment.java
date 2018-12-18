package com.example.idonava.androidproject.menu_activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.idonava.androidproject.R;

public class CounterFragment extends Fragment implements View.OnClickListener {

    TextView tv;
    Button createButton;
    Button startButton;
    Button cancelButton;
    private IAsyncTaskEvents callbackListener;
    public final static String FRAGMENT_TYPE = "fragment_type";


    public CounterFragment() {

    }

    public static CounterFragment newInstance(String framgentType)
    {
        CounterFragment counterFragment = new CounterFragment();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString(CounterFragment.FRAGMENT_TYPE, framgentType);//put string, int, etc in bundle with a key value
        counterFragment.setArguments(data);//Finally set argument bundle to fragment
        return counterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counter, container, false);

        tv = (TextView) rootView.findViewById(R.id.textView);
        createButton = (Button) rootView.findViewById(R.id.create_button);
        startButton = (Button) rootView.findViewById(R.id.start_button);
        cancelButton = (Button) rootView.findViewById(R.id.cancel_button);

        createButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        //UNPACK OUR DATA FROM OUR BUNDLE
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String fragmentText = this.getArguments().getString(FRAGMENT_TYPE).toString();
            tv.setText(fragmentText);
        }
        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity hostActivity = getActivity();
        if (hostActivity != null && hostActivity instanceof IAsyncTaskEvents) {
            callbackListener = (IAsyncTaskEvents) hostActivity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackListener = null;
    }

    public void updateFragmentText(String text) {
        if (tv != null) {
            tv.setText(text);
        }
    }

    @Override
    public void onClick(View v) {
        if (!isAdded() || callbackListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.create_button:
                callbackListener.createAsyncTask();
                break;
            case R.id.start_button:
                callbackListener.startAsyncTask();
                break;
            case R.id.cancel_button:
                callbackListener.cancelAsyncTask();
                break;
        }
    }


}
