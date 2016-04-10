package com.dev.irobot.filter;

import com.dev.irobot.subscribe.PackageSubscribe;

public final class PackageFilter implements Filter {
    @Override
    public boolean accept(String name) {
        return PackageSubscribe.getInstance().getSubscribePackage().contains(name);
    }
}
