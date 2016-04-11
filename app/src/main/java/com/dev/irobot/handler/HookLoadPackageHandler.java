package com.dev.irobot.handler;

import com.dev.irobot.subscribe.HookMethodHandlerSubscriber;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.Map;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class HookLoadPackageHandler {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        HookMethodHandlerSubscriber subscriber = HookMethodHandlerSubscriber.getInstance();
        for (Map.Entry<String,HookMethodHandler> entity: subscriber.getHookMethodHandlers().entrySet()) {
            if(loadPackageParam.packageName.equals(entity.getKey())){
                entity.getValue().findAndHookMethod();
            }
        }
    }
}
