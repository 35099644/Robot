package com.dev.irobot.handler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


/**
 * hook处理器,
 * 已经在assets/xposed_init中配置
 */
public final class HookLoadPackage implements IXposedHookLoadPackage {

    private final HookLoadPackageHandler hookLoadPackageHandler = new HookLoadPackageHandler();
    /**
     * hook并拦截方法
     * @param loadPackageParam
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        hookLoadPackageHandler.handleLoadPackage(loadPackageParam);
    }
}
