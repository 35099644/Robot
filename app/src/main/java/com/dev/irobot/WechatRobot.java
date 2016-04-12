package com.dev.irobot;

import android.app.Application;

/**
 * Created by Jacky on 2016/4/12.
 */
public class WechatRobot extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.getInstance().setContext(this);
    }
}
