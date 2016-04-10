package com.dev.irobot.filter;

import com.dev.irobot.subscribe.PackageSubscriber;

public final class PackageFilter implements Filter {
    @Override
    public boolean accept(String name) {
        return PackageSubscriber.getInstance().getSubscribePackage().contains(name);
    }
}
