package com.dev.irobot.wechat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.dev.irobot.ContextHolder;
import com.dev.irobot.handler.HookMethodHandler;
import com.dev.irobot.handler.MethodHook;
import com.dev.irobot.tool.Log;
import com.dev.irobot.wechat.event.DumpEvent;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;
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

    //LauncherUI中底部微信按钮
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_WEIXIN  = WECHAT_ACTIVITY_LAUNCHERUI+ "BottomBar" + "微信";
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_CONTACTS= WECHAT_ACTIVITY_LAUNCHERUI+ "BottomBar" + "通讯录";
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_FIND    = WECHAT_ACTIVITY_LAUNCHERUI+ "BottomBar" + "发现";
    private static final String WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_MINE    = WECHAT_ACTIVITY_LAUNCHERUI+ "BottomBar" + "我";

    private Bus bus = new Bus(ThreadEnforcer.ANY);
    private static final Map<String,List<IdEntry>> viewUseForByActivity = new ConcurrentHashMap<String, List<IdEntry>>();

    static {

        //com.tencent.mm.ui.LauncherUI view begin
        List<IdEntry> useFor = new ArrayList<IdEntry>();
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_WEIXIN, "主界面底部微信按钮"));
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_CONTACTS,"主界面联系人按钮"));
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_FIND,"主界面通讯录按钮"));
        useFor.add(new IdEntry(WECHAT_ACTIVITY_LAUNCHERUI_VIEW_BOTTOM_MINE,"主界面我按钮"));
        viewUseForByActivity.put(WECHAT_ACTIVITY_LAUNCHERUI, useFor);
        //com.tencent.mm.ui.LauncherUI view begin

    }
    private volatile Activity currentActivity;

    public WechatHookMethodHandler(){
        bus.register(this);
    }
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

            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
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
                    final View rootView = activity.getWindow().getDecorView();
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
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onLayoutChange(final View rootView) {
        visitViewThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                visiteViewTree(rootView, new StringBuffer("-"));
            }
        });
    }

    private void visiteViewTree(View rootView, StringBuffer format) {
        if(rootView instanceof ViewGroup){
            int count = ((ViewGroup) rootView).getChildCount();
            StringBuilder builder = new StringBuilder();
            builder.append("visiteViewTree "+","+format+"view:"+rootView+", child:"+count);
            Log.d(TAG,builder.toString());

            format.append("-");
            for(int i = 0; i < count; i++){
                visiteViewTree(((ViewGroup) rootView).getChildAt(i), format);
            }
        }else {

            //如果是有文字显示的控件. Button, CheckBox等很多控件都是TextView子类
            StringBuilder builder = new StringBuilder();
            builder.append("visiteViewTree "+","+format+"view:"+rootView);
            if(rootView instanceof TextView){
                String text = ((TextView) rootView).getText().toString();
                if(!text.isEmpty()){
                    builder.append(((TextView) rootView).getText().toString());
                }
            }
            Log.d(TAG,builder.toString());

        }
    }


    private void visiteViewTree(View rootView, StringBuffer format, VisiteViewCallback callback) {
        if(callback != null){
            callback.onVisite(rootView);
        }
        if(rootView instanceof ViewGroup){
            int count = ((ViewGroup) rootView).getChildCount();
            StringBuilder builder = new StringBuilder();
            builder.append("visiteClickedViewTree "+format+"view:"+rootView+", child:"+count);
            Log.i(TAG,builder.toString());

            format.append("-");
            for(int i = 0; i < count; i++){
                visiteViewTree(((ViewGroup) rootView).getChildAt(i), format, callback);
            }

        }else {

            //如果是有文字显示的控件. Button, CheckBox等很多控件都是TextView子类
            StringBuilder builder = new StringBuilder();
            builder.append("visiteClickedViewTree "+format+"view:"+rootView);
            if(rootView instanceof TextView){
                String text = ((TextView) rootView).getText().toString();
                if(!text.isEmpty()){
                    builder.append(((TextView) rootView).getText().toString());
                }
            }

            Log.i(TAG,builder.toString());
        }
    }



    class DumpViewCallback implements VisiteViewCallback {

        private final DumpView dumpView = new DumpView();
        private final View rootView;
        public DumpViewCallback(View rootView) {
            this.rootView = rootView;

        }

        @Override
        public void onBegin() {
            dumpView.setAttachedActivity(currentActivity.getClass().getName());
            dumpView.setKey(getIdentifierName(rootView));
        }

        @Override
        public void onVisite(View view) {
            dumpView.getValues().add(getIdentifierName(view));
        }

        @Override
        public void onEnded() {
            Gson gson = new Gson();
            final String dump = gson.toJson(dumpView).toLowerCase();
            Log.i(TAG,"dump:"+dump);
            final List<IdEntry> entrys = viewUseForByActivity.get(dumpView.getAttachedActivity());

            int size = entrys.size();
            if(size == 0){
                return;
            }

            bus.post(new DumpEvent(dump, entrys));


        }

    }

    @Subscribe
    public void onBusEvent(final DumpEvent event){
        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(currentActivity).setTitle("控件校对").setAdapter(new ArrayAdapter(currentActivity, android.R.layout.simple_list_item_1,event.getEntrys()){
                    @Override
                    public String getItem(int position) {
                        IdEntry o = (IdEntry) super.getItem(position);
                        return  o.getName();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG,"onDumpView dump:"+event.getDump()+", entry:"+event.getEntrys().get(which));
                        //TODO 这里要将数据保存起来
                    }
                }).show();
            }
        });
    }

    /**
     *
     * a wrapper method of onClick(View)
     * @param v
     */
    private void onViewClick(final View v) {
        visitViewThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                DumpViewCallback dumpViewCallback = new DumpViewCallback(v);
                dumpViewCallback.onBegin();
                visiteViewTree(v, new StringBuffer("-"), dumpViewCallback);
                dumpViewCallback.onEnded();
            }
        });
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
        out.append(' ');

        final int id = view.getId();
        if (id != View.NO_ID) {
            out.append(" #");
            out.append(Integer.toHexString(id));
            final Resources r = view.getResources();
            if ((id >>> 24) != 0 && r != null) {
                try {
                    String pkgname;
                    switch (id&0xff000000) {
                        case 0x7f000000:
                            pkgname="app";
                            break;
                        case 0x01000000:
                            pkgname="android";
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
        out.append("}");
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




    class OnClickListenerWrapper implements View.OnClickListener{

        private final View.OnClickListener onClickListener;
        public OnClickListenerWrapper(View.OnClickListener onClickListener){
            this.onClickListener = onClickListener;
        }
        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);

            onViewClick(v);
        }
    }

    public static interface VisiteViewCallback {
        void onBegin();
        void onVisite(View view);
        void onEnded();
    }

    public static class IdEntry{
        private final String id;
        private final String name;

        public IdEntry(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            IdEntry idEntry = (IdEntry) o;

            return id != null ? id.equals(idEntry.id) : idEntry.id == null;

        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("IdEntry{");
            sb.append("id='").append(id).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class DumpView {
        private String key;
        private String attachedActivity;
        private final List<String> values = new ArrayList<String>();

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getAttachedActivity() {
            return attachedActivity;
        }

        public void setAttachedActivity(String attachedActivity) {
            this.attachedActivity = attachedActivity;
        }

        public List<String> getValues() {
            return values;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            DumpView dumpView = (DumpView) o;

            if (key != null ? !key.equals(dumpView.key) : dumpView.key != null) {
                return false;
            }
            if (attachedActivity != null ? !attachedActivity.equals(dumpView.attachedActivity) : dumpView.attachedActivity != null) {
                return false;
            }
            return values != null ? values.equals(dumpView.values) : dumpView.values == null;

        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (attachedActivity != null ? attachedActivity.hashCode() : 0);
            result = 31 * result + (values != null ? values.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DumpView{");
            sb.append("key='").append(key).append('\'');
            sb.append(", attachedActivity='").append(attachedActivity).append('\'');
            sb.append(", values=").append(values);
            sb.append('}');
            return sb.toString();
        }
    }

}
