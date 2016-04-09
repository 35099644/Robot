package com.dev.irobot.tool;

import com.dev.irobot.BuildConfig;

import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;


public final class Log {

    public static final int MIN_LEVEL = VERBOSE;

    private static final int LEVEL = BuildConfig.DEBUG ? VERBOSE : MIN_LEVEL;
    private Log() {
    }

    public static int v(String tag, String msg) {
        return println(VERBOSE, tag, msg);
    }


    public static int v(String tag, String msg, Throwable tr) {
        return println(VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
    }


    public static int d(String tag, String msg) {
        return println(DEBUG, tag, msg);
    }


    public static int d(String tag, String msg, Throwable tr) {
        return println(DEBUG, tag, msg + '\n' + getStackTraceString(tr));
    }


    public static int i(String tag, String msg) {
        return println(INFO, tag, msg);
    }


    public static int i(String tag, String msg, Throwable tr) {
        return println(INFO, tag, msg + '\n' + getStackTraceString(tr));
    }


    public static int w(String tag, String msg) {
        return println(WARN, tag, msg);
    }


    public static int w(String tag, String msg, Throwable tr) {
        return println(WARN, tag, msg + '\n' + getStackTraceString(tr));
    }


    public static int w(String tag, Throwable tr) {
        return println(WARN, tag, getStackTraceString(tr));
    }


    public static int e(String tag, String msg) {
        return println(ERROR, tag, msg);
    }


    public static int e(String tag, String msg, Throwable tr) {
        return println(ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }


    public static String getStackTraceString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }


    public static int println(int priority, String tag, String msg) {
        if(priority < LEVEL){
            return 0;
        }
        return android.util.Log.println(priority, tag, msg);
    }


}
