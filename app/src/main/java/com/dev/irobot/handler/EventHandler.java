package com.dev.irobot.handler;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;


public interface EventHandler {

    void handleAccessibilityEvent(AccessibilityService accessibilityService, AccessibilityEvent event);
}
