package com.dev.irobot.subscribe;

import com.dev.irobot.handler.HookHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class HookHandlerSubscribe {
    private final Map<String, HookHandler>  HOOK_HANDLERS  = new HashMap<String, HookHandler>();

    public static final HookHandlerSubscribe SUBSCRIBE = new HookHandlerSubscribe();

    public static HookHandlerSubscribe getInstance(){
        return SUBSCRIBE;
    }

    public Map<String, HookHandler> getSubscribeHandlers() {
        return HOOK_HANDLERS;
    }
}
