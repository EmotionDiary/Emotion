package com.example.ds.emodiary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jihu0 on 2018-06-26.
 */

public class TwoFragment extends Fragment {

    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);
        context = view.getContext();


        return view;
        //SocketClient sc = new SocketClient(getApplicationContext());

    }
}










