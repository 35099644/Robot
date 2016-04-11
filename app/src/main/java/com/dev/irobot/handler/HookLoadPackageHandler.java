package com.dev.irobot.handler;

import com.dev.irobot.subscribe.HookMethodHandlerSubscriber;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class HookLoadPackageHandler {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        HookMethodHandlerSubscriber subscriber = HookMethodHandlerSubscriber.getInstance();
        HookMethodHandler hookMethodHandler = subscriber.getHookMethodHandlers().get(loadPackageParam.packageName);
        if(hookMethodHandler !=null ){
            hookMethodHandler.findAndHookMethod();
        }
    }
}
