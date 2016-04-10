package com.dev.irobot.handler;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import com.dev.irobot.filter.Filter;
import com.dev.irobot.filter.PackageFilter;
import com.dev.irobot.subscribe.EventHandlerSubscribe;


public final class RobotEventProcesser implements EventHandler{

    private final Filter filter = new PackageFilter();
    /**
     * 处理AccessibilityService中的AccessibilityEvent事件
     * @param event
     */
    @Override
    public void handleAccessibilityEvent(AccessibilityService accessibilityService, AccessibilityEvent event) {
        if(filter.accept(event.getPackageName().toString())){
            for(EventHandler handler : EventHandlerSubscribe.getInstance().getSubscribeHandlers().values()){
                handler.handleAccessibilityEvent(accessibilityService,event);
            }
        }
    }


}
