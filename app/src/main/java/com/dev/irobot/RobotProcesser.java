package com.dev.irobot;

import android.view.accessibility.AccessibilityEvent;
import com.dev.irobot.handler.EventHandler;
import com.dev.irobot.handler.HookHandler;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


/**
 * hook处理器,
 * 已经在assets/xposed_init中配置
 */
public class RobotProcesser implements EventHandler, HookHandler {

    /**
     * 处理AccessibilityService中的AccessibilityEvent事件
     * @param event
     */
    @Override
    public void handleAccessibilityEvent(AccessibilityEvent event) {

    }

    /**
     * hook并拦截方法
     * @param loadPackageParam
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {

    }
}
