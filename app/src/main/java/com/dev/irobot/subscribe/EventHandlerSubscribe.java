package com.dev.irobot.subscribe;

import com.dev.irobot.handler.EventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class EventHandlerSubscribe {
    private final Map<String, EventHandler> EVENT_HANDLERS = new HashMap<String, EventHandler>();

    public static final EventHandlerSubscribe SUBSCRIBE = new EventHandlerSubscribe();

    public static EventHandlerSubscribe getInstance(){
        return SUBSCRIBE;
    }

    public Map<String, EventHandler> getSubscribeHandlers() {
        return EVENT_HANDLERS;
    }
}
