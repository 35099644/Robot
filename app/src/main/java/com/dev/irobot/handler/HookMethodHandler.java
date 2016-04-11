package com.dev.irobot.handler;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Jacky on 2016/4/10.
 */
public interface HookMethodHandler {
    void findAndHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam);
}
