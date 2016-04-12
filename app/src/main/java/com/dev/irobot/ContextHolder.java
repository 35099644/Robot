package com.dev.irobot;

import android.content.Context;

/**
 * Created by Jacky on 2016/4/12.
 */
public class ContextHolder {
    public static final ContextHolder CONTEXT_HOLDER = new ContextHolder();


    private volatile Context context;
    public static ContextHolder getInstance(){
        return CONTEXT_HOLDER;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
