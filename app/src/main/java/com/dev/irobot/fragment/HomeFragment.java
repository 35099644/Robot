package com.dev.irobot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.irobot.R;
import com.dev.irobot.support.SupportFragment;

public class HomeFragment extends SupportFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_home, container, false);
    }

}
