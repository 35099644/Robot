package com.dev.irobot.wechat;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.dev.irobot.activity.MainActivity;
import com.dev.irobot.handler.HookMethodHandler;
import com.dev.irobot.handler.MethodHook;
import com.dev.irobot.tool.Log;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Jacky on 2016/4/10.
 * @see TelephonyManager#getDeviceId()
 * @see TelephonyManager#getCellLocation()
 * @see TelephonyManager#getNeighboringCellInfo()
 * @see WifiManager#getScanResults()
 * @see LocationManager#getGpsStatus(GpsStatus)
 * @see LocationListener#onLocationChanged(Location)
 *
 */
public class WechatHookMethodHandler implements HookMethodHandler {
    private static final String TAG = WechatHookMethodHandler.class.getSimpleName();


    @Override
    public void findAndHookMethod() {
        XposedHelpers.findAndHookMethod(MainActivity.class, "getDeviceId", new MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod");
            }

        });


        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getDeviceId", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });


        /**hook lbs begin*/
        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getCellLocation", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getNeighboringCellInfo", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(WifiManager.class, "getScanResults", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "getGpsStatus", GpsStatus.class, new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });


        /**hook lbs ended*/
    }
}
