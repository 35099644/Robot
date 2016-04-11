package com.dev.irobot.filter;

import com.dev.irobot.subscribe.HookMethodHandlerSubscriber;

public final class PackageFilter implements Filter {
    @Override
    public boolean accept(String name) {
        return HookMethodHandlerSubscriber.getInstance().getHookMethodHandlers().keySet().contains(name);
    }
}
