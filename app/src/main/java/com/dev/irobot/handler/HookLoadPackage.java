package com.dev.irobot.handler;

import com.dev.irobot.subscribe.HookMethodHandlerSubscriber;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


/**
 * hook处理器,
 * 已经在assets/xposed_init中配置
 */
public final class HookLoadPackage implements IXposedHookLoadPackage {
    /**
     * hook并拦截方法, 如果hook了新方法必须重启一次才会生效.
     * @param loadPackageParam
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        HookMethodHandlerSubscriber subscriber = HookMethodHandlerSubscriber.getInstance();
        HookMethodHandler hookMethodHandler = subscriber.getHookMethodHandlers().get(loadPackageParam.packageName);
        if(hookMethodHandler !=null ){
            hookMethodHandler.findAndHookMethod(loadPackageParam);
        }
    }
}
