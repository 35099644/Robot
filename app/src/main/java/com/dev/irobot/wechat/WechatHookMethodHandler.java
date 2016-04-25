package com.dev.irobot.wechat;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.dev.irobot.ContextHolder;
import com.dev.irobot.Config.Config;
import com.dev.irobot.event.ViewClickEvent;
import com.dev.irobot.handler.HookMethodHandler;
import com.dev.irobot.handler.MethodHook;
import com.dev.irobot.tool.IdEntry;
import com.dev.irobot.tool.Log;
import com.dev.irobot.tool.VisiteViewCallback;
import com.google.gson.Gson;
import com.squareup.otto.EventBus;
import com.squareup.otto.Subscribe;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jacky on 2016/4/10.
 * 如果有公共父类只hook 公共父类就可以,不用每个类都hook, method只hook公共方法即可.
 * 打印的Log信息要到被hook的应用下去查看,比如com.tencent.mm,
 * hook前先反编译源码看有没有混淆,混淆了得话必须hook混淆后的代码
 */
public class WechatHookMethodHandler implements HookMethodHandler {
    private static final String TAG = WechatHookMethodHandler.class.getSimpleName();

    /**微信activity列表 begin*/
    //安装程序后,分别进入微信的各个界面,如果当前界面是activity 则会有类似日志android.app.Activity.onResume(), param:[], object:com.tencent.mm.ui.LauncherUI@4ae6f760,从中可以获取到Activity名字，如果有用到就在代码中写入
    //微信界面中采用了大量Fragment,如果界面切换的时候看不到Activity的相关日志说明是Fragment之间在切换
    //微信主界面
    private static final String WECHAT_ACTIVITY_LAUNCHERUI = "com.tencent.mm.ui.LauncherUI";


    /**微信activity列表 ended*/

    //LauncherUI中底部微信按钮,所有要用到的按钮在这里声明 格式为 activity名子, 所在位置  按钮上的文字 这几个字符串的拼接，以英文冒号分割
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_WEIXIN  = WECHAT_ACTIVITY_LAUNCHERUI+ ":BottomBar:" + "微信";
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_CONTACTS= WECHAT_ACTIVITY_LAUNCHERUI+ ":BottomBar:" + "通讯录";
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_FIND    = WECHAT_ACTIVITY_LAUNCHERUI+ ":BottomBar:" + "发现";
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_MINE    = WECHAT_ACTIVITY_LAUNCHERUI+ ":BottomBar:" + "我";

    private static final Map<String,List<IdEntry>> viewUseForByActivity = new ConcurrentHashMap<String, List<IdEntry>>();

    //暂时保存点击微信上的控件时我们获取到的view信息，以后这些信息可以导出到文件， 不用每次都校准。
    //key 对应IdEntry中的id，也就是我们给view起的名字。
    //value 对应view及其子控件的信息
    private final Map<String,String> viewDumps = new ConcurrentHashMap<String, String>();

    //key 对应view及其子控件的信息
    //value 更具key查找的view
    private final Map<String,View> viewCache= new ConcurrentHashMap<String, View>();

    static {

        //所有要用到的按钮在这里注册，
        //com.tencent.mm.ui.LauncherUI view begin
        List<IdEntry> useFor = new ArrayList<IdEntry>();
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_WEIXIN, "主界面底部微信按钮"));
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_CONTACTS,"主界面通讯录按钮"));
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_FIND,"主界面发现按钮"));
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_MINE,"主界面我按钮"));
        viewUseForByActivity.put(WECHAT_ACTIVITY_LAUNCHERUI, useFor);
        //com.tencent.mm.ui.LauncherUI view begin

    }
    private volatile Activity currentActivity;
    private volatile View rootView;
    public WechatHookMethodHandler(){
        EventBus.getDefault().register(this);
    }
    @Override
    public void findAndHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Log.v(TAG,"load package:"+loadPackageParam.packageName);

        loadData();



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

                if(param.thisObject instanceof Activity){
                    final Activity activity = (Activity) param.thisObject;
                    currentActivity = activity;
                    Intent intent = activity.getIntent();
                    if(intent != null){
                        Log.v(TAG, "Intent action:"+intent.getAction()+", component:"+intent.getComponent());
                    }
                }



            }
        });


        XposedHelpers.findAndHookMethod(Activity.class, "setContentView", View.class, new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.v(TAG,"beforeHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
            }


            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.v(TAG,"afterHookerMethod:"+param.method+", param:"+ Arrays.toString(param.args)+", object:"+param.thisObject);
                if(param.thisObject instanceof Activity){
                    Activity activity = (Activity) param.thisObject;
                    currentActivity = activity;
                    rootView = (View) param.args[0];
                }
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
                if(param.thisObject instanceof Activity){
                    Activity activity = (Activity) param.thisObject;
                    currentActivity = activity;
                    rootView = activity.getWindow().getDecorView();
                    View.OnLayoutChangeListener layoutChangeListener = (View.OnLayoutChangeListener) rootView.getTag(View.OnLayoutChangeListener.class.hashCode()+rootView.getContext().hashCode());
                    if(layoutChangeListener == null){
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                            layoutChangeListener = new View.OnLayoutChangeListener() {
                                @Override
                                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                                    WechatHookMethodHandler.this.onLayoutChange(v);
                                }
                            };
                            rootView.setTag(View.OnLayoutChangeListener.class.hashCode()+rootView.getContext().hashCode());
                            rootView.addOnLayoutChangeListener(layoutChangeListener);
                        }

                    }
                }
            }
        });


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
                if(param.args[2] instanceof Notification){
                    Notification notification = (Notification) param.args[2];

                    Log.v(TAG,"Notification notification tickerText:"+notification.tickerText);
                    if(notification.tickerText.toString().startsWith(":")){
                        //广告新闻
                        return;
                    }
                    if (notification.tickerText.toString().contains("请加你为好友") && !notification.tickerText.toString().contains(":")) {
                        onNotifitionAcceptFriendMessage();
                    } else if (notification.tickerText.toString().contains(":")) {
                        String text = notification.tickerText.toString();
                        String name = text.substring(0, text.indexOf(":"));
                        onNotifitionNewMessage(name);
                    }
                }

            }
        });

        XposedHelpers.findAndHookMethod(View.class, "setOnClickListener", View.OnClickListener.class, new MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if(param.thisObject instanceof View){
                    //修改setOnClickListener参数
                    if(param.args[0] instanceof OnClickListenerWrapper){
                    }else{
                        if(param.args[0] instanceof View.OnClickListener){
                            param.args[0] = new OnClickListenerWrapper((View.OnClickListener) param.args[0]);
                        }
                    }
                }

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

    }




    private final ExecutorService visitViewThreadPool = Executors.newCachedThreadPool();
    /**
     * 当界面发生变化的时候调用,
     * 需要在这里遍历组件树,查找相应的组建进行处理。
     * @param rootView
     */
    public void onLayoutChange(final View rootView) {
        visitViewThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FindViewCallback callback = new FindViewCallback();
                callback.onBegin();
                visiteViewTree(rootView, new StringBuffer("-"), callback);
                callback.onEnded();
            }
        });
    }

    private void visiteViewTree(View rootView, StringBuffer format, VisiteViewCallback callback) {
        if(callback != null){
            callback.onVisite(rootView);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("visiteViewTree id:"+rootView.getId()+format+"view:"+getIdentifierName(rootView));
        if(format != null){
            if(callback != null){
                Log.i(TAG,builder.toString());
            }else {
                Log.v(TAG,builder.toString());
            }
        }

        if(rootView instanceof ViewGroup){
            int count = ((ViewGroup) rootView).getChildCount();



            if(format!=null){
                format.append("-");
            }

            for(int i = 0; i < count; i++){
                visiteViewTree(((ViewGroup) rootView).getChildAt(i), format, callback);
            }

        }
    }

    class FindViewCallback implements VisiteViewCallback {

        @Override
        public void onBegin() {

        }

        @Override
        public void onVisite(View view) {
            for(Map.Entry<String, String> entry : viewDumps.entrySet()){
                if(getIdentifierName(view).equals(entry.getValue())){
                    viewCache.put(entry.getValue(), view);
                    Log.v(TAG, "find entry id:"+entry.getKey()+", dump:"+entry.getValue());
                }
            }
        }

        @Override
        public void onEnded() {
        }
    }



    private void saveData() {
        ViewDumpEntry viewDumpEntry = new ViewDumpEntry();
        viewDumpEntry.setViewDumps(viewDumps);
        Config.getConfig().getProperties().put("viewdumpentry", new Gson().toJson(viewDumpEntry));
        Config.getConfig().save();
    }

    private void loadData() {
        Config config = Config.getConfig();
        config.load();
        String dumps = config.getProperties().getProperty("viewdumpentry","");
        ViewDumpEntry viewDumpEntry = new Gson().fromJson(dumps, ViewDumpEntry.class);
        if(viewDumpEntry != null){
            Map<String,String> temp = viewDumpEntry.getViewDumps();
            if(temp != null){
                viewDumps.putAll(temp);
            }
        }
    }

    /**
     * 我们点击微信中的按钮时,这个方法会调用并分析是那个view被点击，同时会将被点击view及子控件信息记录下来,这样我们以后做跳转时
     * 可以直接更具这些信息在组件树中查找到这个view,
     * @param event
     */
    @Subscribe
    public void onViewClickEvent(final ViewClickEvent event) {
        final String dump = getIdentifierName(event.getView());
        Log.i(TAG, "onViewClickEvent activity:" + currentActivity + ", dump:" + dump);
        final List<IdEntry> entrys = viewUseForByActivity.get(currentActivity.getClass().getName());

        int size = entrys.size();
        if (size == 0) {
            return;
        }

        Dialog dialog = new AlertDialog.Builder(currentActivity).setTitle("控件校对").setAdapter(new ArrayAdapter(currentActivity, android.R.layout.simple_list_item_1, entrys) {
            @Override
            public String getItem(int position) {
                IdEntry o = (IdEntry) super.getItem(position);
                return o.getDescribe();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onDumpView dump:" + dump + ", entry:" + entrys.get(which));
                viewDumps.put(entrys.get(which).getId(), dump);
                visitViewThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        saveData();
                    }
                });
            }
        }).create();

        dialog.show();

    }

    /**
     * 当通知栏收到新朋友添加请求的时候调用
     */
    public void onNotifitionAcceptFriendMessage(){
        gotoAcceptFriendUI();
    }

    /**
     * 当通知栏收到聊天消息的时候调用
     */
    public void onNotifitionNewMessage(String name){
        gotoChatUI(name);
    }


    /**
     * 跳转到好友请求界面
     */
    private void gotoAcceptFriendUI(){
        //TODO
    }

    /**
     * 跳转到微信消息界面
     */
    private void gotoMessageUI(){
        if(isInLauncherUI()){

        }
    }


    private boolean isInLauncherUI(){
        if(currentActivity != null && currentActivity.getClass().getName().equals(WECHAT_ACTIVITY_LAUNCHERUI)){
            return true;
        }
        return false;
    }


    private String getIdentifierName(View view){
        StringBuilder out = new StringBuilder(128);
        out.append(view.getClass().getName());
        out.append('{');
        out.append(view.isFocusable() ? 'F' : '.');
        out.append(view.isEnabled() ? 'E' : '.');
        out.append(view.isHorizontalScrollBarEnabled() ? 'H' : '.');
        out.append(view.isVerticalScrollBarEnabled() ? 'V' : '.');
        out.append(view.isClickable() ? 'C' : '.');
        out.append(view.isLongClickable() ? 'L' : '.');
        out.append('[');
        out.append(view.getLeft());
        out.append(',');
        out.append(view.getTop());
        out.append(',');
        out.append(view.getRight());
        out.append(',');
        out.append(view.getBottom());
        out.append("]");
        out.append(".");
        out.append('[');
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        out.append("x:"+point[0]);
        out.append(",");
        out.append("y:"+point[1]);
        out.append("]");
        final int id = view.getId();
        if (id != View.NO_ID) {
            out.append(" #");
            out.append(Integer.toHexString(id));
            final Resources r = view.getResources();
            if ((id >>> 24) != 0 && r != null) {
                try {
                    String pkgname;
                    switch (id & 0xff000000) {
                        case 0x7f000000:
                            pkgname = "app";
                            break;
                        case 0x01000000:
                            pkgname = "android";
                            break;
                        default:
                            pkgname = r.getResourcePackageName(id);
                            break;
                    }
                    String typename = r.getResourceTypeName(id);
                    String entryname = r.getResourceEntryName(id);
                    out.append(" ");
                    out.append(pkgname);
                    out.append(":");
                    out.append(typename);
                    out.append("/");
                    out.append(entryname);
                } catch (Resources.NotFoundException e) {
                }
            }
        }


        out.append("}");

        out.append(".");
        if(view instanceof ViewGroup){
            out.append(((ViewGroup) view).getChildCount());
        }else {
            out.append("0");
        }

        if(view instanceof TextView && !(view instanceof EditText)){
            String text = ((TextView) view).getText().toString();
            if(!text.isEmpty()){
                out.append(text);
            }else {
                out.append(".");
            }
        }else{
            out.append(".");
        }
        return out.toString();
    }

    /**
     * 跳转到和某人的聊天界面
     * @param name
     */
    private void gotoChatUI(String name){
        //TODO
    }



    public void launchWeichat(){
        Intent intent = launchIntentForPackage("com.tencent.mm");
        ContextHolder.getInstance().getContext().startActivity(intent);
    }

    public Intent launchIntentForPackage(String packageName){
        Context context = ContextHolder.getInstance().getContext();
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }




    private class OnClickListenerWrapper implements View.OnClickListener{

        private final View.OnClickListener onClickListener;
        public OnClickListenerWrapper(View.OnClickListener onClickListener){
            this.onClickListener = onClickListener;
        }
        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
            EventBus.getDefault().post(new ViewClickEvent(v));
        }
    }

    class ViewDumpEntry {
        private Map<String,String> viewDumps = new ConcurrentHashMap<String, String>();

        public void setViewDumps(Map<String, String> viewDumps) {
            if(viewDumps != null && !viewDumps.isEmpty()){
                this.viewDumps.putAll(viewDumps);
            }
        }
        public Map<String, String> getViewDumps() {
            return viewDumps;
        }
    }

}
