package com.dev.irobot.subscribe;

import com.dev.irobot.interceptor.HookMethodInterceptor;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class HookMethodInterceptorSubscriber {
    private static final Set<HookMethodInterceptor> subscribeHookMethodInterceptor = new CopyOnWriteArraySet<HookMethodInterceptor>();

    public static final HookMethodInterceptorSubscriber SUBSCRIBER = new HookMethodInterceptorSubscriber();

    public static HookMethodInterceptorSubscriber getInstance(){
        return SUBSCRIBER;
    }

    public Set<HookMethodInterceptor> getSubscriHookMethodInterceptor() {
        return subscribeHookMethodInterceptor;
    }
}
