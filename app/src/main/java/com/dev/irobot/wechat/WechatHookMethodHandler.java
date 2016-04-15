package com.dev.irobot.wechat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import com.dev.irobot.ContextHolder;
import com.dev.irobot.handler.HookMethodHandler;
import com.dev.irobot.handler.MethodHook;
import com.dev.irobot.tool.Log;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.Arrays;

/**
 * Created by Jacky on 2016/4/10.
 * 如果有公共父类只hook 公共父类就可以,不用每个类都hook, method只hook公共方法即可.
 * 打印的log信息要到被hook的应用下去查看,比如com.tencent.mm,
 */
public class WechatHookMethodHandler implements HookMethodHandler {
    private static final String TAG = WechatHookMethodHandler.class.getSimpleName();


    @Override
    public void findAndHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Log.v(TAG,"load package:"+loadPackageParam.packageName);

        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getDeviceId", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });


        /**hook lbs begin*/

        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getCellLocation", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });


        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getNeighboringCellInfo", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });

        XposedHelpers.findAndHookMethod(WifiManager.class, "getScanResults", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });

        XposedHelpers.findAndHookMethod(LocationManager.class, "getGpsStatus", GpsStatus.class, new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });

        /**hook lbs ended*/

        XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });

        XposedHelpers.findAndHookMethod(Activity.class, "onResume", new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });

//        XposedHelpers.findAndHookMethod(Activity.class, "onAttachedToWindow", new MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
//           }
//        });

//        XposedHelpers.findAndHookMethod(TextView.class, "setText",CharSequence.class, TextView.BufferType.class, new MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
//            }
//        });

      XposedHelpers.findAndHookMethod(NotificationManager.class, "notify",String.class, int.class, Notification.class, new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }
        });


    }




    public void launchWeichat(){
        Intent intent = launchIntentForPackage("com.tencent.mm");
        ContextHolder.getInstance().getContext().startActivity(intent);
    }

    public Intent launchIntentForPackage(String packageName){
        Context context = ContextHolder.getInstance().getContext();
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }



}
