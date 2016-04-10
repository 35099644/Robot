package com.dev.irobot.handler;

import android.view.accessibility.AccessibilityEvent;
import com.dev.irobot.filter.Filter;
import com.dev.irobot.filter.PackageFilter;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import java.util.HashMap;
import java.util.Map;


/**
 * hook处理器,
 * 已经在assets/xposed_init中配置
 */
public final class RobotProcesser implements EventHandler, HookHandler {

    private final Map<String, EventHandler> EVENT_HANDLERS = new HashMap<String, EventHandler>();
    private final Map<String, HookHandler>  HOOK_HANDLERS  = new HashMap<String, HookHandler>();

    private final Filter filter = new PackageFilter();
    /**
     * 处理AccessibilityService中的AccessibilityEvent事件
     * @param event
     */
    @Override
    public void handleAccessibilityEvent(AccessibilityEvent event) {
        if(filter.accept(event.getPackageName().toString())){
            for(EventHandler handler : EVENT_HANDLERS.values()){
                handler.handleAccessibilityEvent(event);
            }
        }
    }

    /**
     * hook并拦截方法
     * @param loadPackageParam
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        if(filter.accept(loadPackageParam.packageName)){
            for(HookHandler handler : HOOK_HANDLERS.values()){
                handler.handleLoadPackage(loadPackageParam);
            }
        }
    }

    /**
     * 添加EventHandler
     * @param name
     * @param eventHandler
     */
    public void addEventHandler(String name, EventHandler eventHandler){
        EVENT_HANDLERS.put(name, eventHandler);
    }

    /**
     * 添加HookHandler
     * @param name
     * @param hookHandler
     */
    public void addHookHandler(String name, HookHandler hookHandler){
        HOOK_HANDLERS.put(name, hookHandler);
    }

    /**
     * 删除EventHandler
     * @param name
     */
    public void removeEventHandler(String name){
        EVENT_HANDLERS.remove(name);
    }

    /**
     * 删除HookHandler
     * @param name
     */
    public void removeHookHandler(String name){
        HOOK_HANDLERS.remove(name);
    }

    /**
     * 判断EventHandler是否已经添加
     * @param name
     * @return
     */
    public boolean containEventHandler(String name){
        return EVENT_HANDLERS.containsKey(name);
    }

    /**
     * 判断HookHandler是否已经添加
     * @param name
     * @return
     */
    public boolean containHookHandler(String name){
        return HOOK_HANDLERS.containsKey(name);
    }
}
