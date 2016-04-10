package com.dev.irobot.handler;

import com.dev.irobot.filter.Filter;
import com.dev.irobot.filter.PackageFilter;
import com.dev.irobot.subscribe.HookHandlerSubscribe;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


/**
 * hook处理器,
 * 已经在assets/xposed_init中配置
 */
public final class RobotHookProcesser implements HookHandler {


    private final Filter filter = new PackageFilter();

    /**
     * hook并拦截方法
     * @param loadPackageParam
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        if(filter.accept(loadPackageParam.packageName)){
            for(HookHandler handler : HookHandlerSubscribe.getInstance().getSubscribeHandlers().values()){
                handler.handleLoadPackage(loadPackageParam);
            }
        }
    }
}
