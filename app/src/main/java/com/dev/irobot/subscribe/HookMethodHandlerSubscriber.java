package com.dev.irobot.subscribe;

import com.dev.irobot.handler.HookMethodHandler;
import com.dev.irobot.wechat.WechatHookMethodHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class HookMethodHandlerSubscriber {
    private static final Map<String,HookMethodHandler> subscribeHookMethodHandlers = new ConcurrentHashMap<String,HookMethodHandler>();

    public static final HookMethodHandlerSubscriber SUBSCRIBER = new HookMethodHandlerSubscriber();

    static {
        subscribeHookMethodHandlers.put("com.tencent.mm", new WechatHookMethodHandler());

        //测试
        subscribeHookMethodHandlers.put("com.dev.irobot", new WechatHookMethodHandler());
    }

    public static HookMethodHandlerSubscriber getInstance(){
        return SUBSCRIBER;
    }

    public Map<String,HookMethodHandler> getHookMethodHandlers() {
        return subscribeHookMethodHandlers;
    }
}
