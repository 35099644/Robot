package com.dev.irobot;

import com.dev.irobot.handler.HookHandler;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Jacky on 2016/4/10.
 */
public class MethodHookHandler implements HookHandler{
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
    }
}
