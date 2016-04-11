package com.dev.irobot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.dev.irobot.service.MonitorService;

public class BootMonitor extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent tent) {
        context.startService(new Intent(context, MonitorService.class));
    }
}
