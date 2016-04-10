package com.dev.irobot;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;
import com.dev.irobot.handler.EventHandler;

/**
 * Created by Jacky on 2016/4/10.
 */
public class ViewEventHandler implements EventHandler {
    @Override
    public void handleAccessibilityEvent(AccessibilityService accessibilityService, AccessibilityEvent event) {
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Parcelable data = event.getParcelableData();
                if(data != null && data instanceof Notification){

                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                break;
        }
    }
}
