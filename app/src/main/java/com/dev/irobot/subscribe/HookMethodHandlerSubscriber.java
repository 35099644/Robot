package com.dev.irobot.subscribe;

import com.dev.irobot.handler.HookMethodHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class HookMethodHandlerSubscriber {
    private static final Set<HookMethodHandler> subscribeHookMethodInterceptor = new CopyOnWriteArraySet<HookMethodHandler>();

    public static final HookMethodHandlerSubscriber SUBSCRIBER = new HookMethodHandlerSubscriber();

    public static HookMethodHandlerSubscriber getInstance(){
        return SUBSCRIBER;
    }

    public Set<HookMethodHandler> getSubscriHookMethodHandler() {
        return subscribeHookMethodInterceptor;
    }
}
