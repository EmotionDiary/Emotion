package com.example.ds.emodiary;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jihu0 on 2018-06-26.
 */

public class FourFragment extends Fragment {

    public FourFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        Button addDiary = (Button)view.findViewById(R.id.addDiary);
        addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 일정 등록하는 FourFragmentAdd로 이동하기
                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction ftrans = fmanager.beginTransaction();
                ftrans.add(R.id.fragment_4,new FourFragmentAdd());
                ftrans.commit();
            }
        });
        return view;
    }
}
