package com.squareup.otto;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Jacky on 2016/4/17.
 */
public class EventBus extends Bus{
    private static final EventBus eventBus = new EventBus(ThreadEnforcer.ANY);
    private Handler handler = new Handler(Looper.getMainLooper());
    public EventBus() {
    }

    public EventBus(String identifier) {
        super(identifier);
    }

    public EventBus(ThreadEnforcer enforcer) {
        super(enforcer);
    }

    public EventBus(ThreadEnforcer enforcer, String identifier) {
        super(enforcer, identifier);
    }

    @Override
    protected void dispatch(final Object event, final EventHandler wrapper) {
        if(Looper.myLooper() == Looper.getMainLooper()){
            super.dispatch(event, wrapper);
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.super.dispatch(event, wrapper);
                }
            });
        }

    }

    public static EventBus getDefault(){
        return eventBus;
    }
}
