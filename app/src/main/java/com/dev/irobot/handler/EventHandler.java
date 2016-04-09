package com.dev.irobot.handler;

import android.view.accessibility.AccessibilityEvent;


public interface EventHandler {

    void handleAccessibilityEvent(AccessibilityEvent event);
}
