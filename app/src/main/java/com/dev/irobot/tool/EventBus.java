package com.dev.irobot.tool;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Jacky on 2016/4/17.
 */
public class EventBus extends Bus{
    private static final EventBus eventBus = new EventBus(ThreadEnforcer.ANY);

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


    public static EventBus getDefault(){
        return eventBus;
    }
}
