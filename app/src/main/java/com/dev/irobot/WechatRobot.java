package com.dev.irobot;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by Jacky on 2016/4/12.
 */
public class WechatRobot extends Application {
    static PatchManager patchManager;
    @Override
    public void onCreate() {
        super.onCreate();

        patchManager = new PatchManager(this);
        patchManager.init(getVersion());
        ContextHolder.getInstance().setContext(this);
    }

    private String getVersion(){
        String version = "1";
        try {
            version = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static class AndFixBroadcastReceiver extends BroadcastReceiver {
        public static final String ACTION_ADDPATCH = "com.dev.irobot_ADDPATCH";
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_ADDPATCH)){
                String path = intent.getStringExtra("path");
                if(path != null){
                    try {
                        WechatRobot.patchManager.removeAllPatch();
                        WechatRobot.patchManager.addPatch(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
