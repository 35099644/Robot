package com.dev.irobot.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Jacky on 2016/4/11.
 */
public class MonitorService extends IntentService {

    public MonitorService(){
        this(MonitorService.class.getSimpleName());
    }
    public MonitorService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
