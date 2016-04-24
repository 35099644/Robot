package com.dev.irobot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.dev.irobot.R;
import com.dev.irobot.fragment.HomeFragment;
import com.dev.irobot.support.SupportActivity;

public class MainActivity extends SupportActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment homeFragment = fragmentManager.findFragmentByTag(HomeFragment.class.getName());
        if(homeFragment == null){
            fragmentManager.beginTransaction().add(R.id.content, new HomeFragment(), HomeFragment.class.getName()).commitAllowingStateLoss();
        }else {
            fragmentManager.beginTransaction().show(homeFragment).commit();
        }
    }

}
