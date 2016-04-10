package com.dev.irobot.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import com.dev.irobot.subscribe.PackageSubscribe;
import com.dev.irobot.tool.HandlerManager;
import com.dev.irobot.tool.Log;

/**
 * xml/accessibility_service_config.xml
 * android:packageNames属性配置关注的apk包名，以英文逗号分隔
 */
public class RobotAccessibilityService extends AccessibilityService {
    private static final String TAG = RobotAccessibilityService.class.getSimpleName();
    public RobotAccessibilityService() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        String[] packageNames = new String[PackageSubscribe.getInstance().getSubscribePackage().size()];

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            AccessibilityServiceInfo accessibilityServiceInfo = getServiceInfo();
            accessibilityServiceInfo.packageNames = PackageSubscribe.getInstance().getSubscribePackage().toArray(packageNames);
            setServiceInfo(accessibilityServiceInfo);
        }
        Log.v(TAG,"onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.v(TAG,"onAccessibilityEvent event:"+event);
        HandlerManager.getEventProcesser().handleAccessibilityEvent(this, event);
    }

    @Override
    public void onInterrupt() {
        Log.v(TAG,"onInterrupt");
    }
}
