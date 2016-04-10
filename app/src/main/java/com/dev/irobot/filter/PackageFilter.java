package com.dev.irobot.filter;

import com.dev.irobot.tool.Subscribe;

public class PackageFilter implements Filter {
    @Override
    public boolean accept(String name) {
        return Subscribe.getInstance().getSubscribePackage().contains(name);
    }
}
