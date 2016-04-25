package com.dev.irobot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.dev.irobot.R;
import com.dev.irobot.support.SupportFragment;

public class HomeFragment extends SupportFragment {
    private Button importToContacts;
    private Button importToWeixin;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_home, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        importToContacts = (Button) view.findViewById(R.id.importToContacts);
        importToContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importMobileNumberToMobile();
            }
        });

        importToWeixin = (Button) view.findViewById(R.id.importToWeixin);
        importToWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void importMobileNumberToMobile(){

    }


}
