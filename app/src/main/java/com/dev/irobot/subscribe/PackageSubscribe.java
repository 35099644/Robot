package com.dev.irobot.subscribe;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class PackageSubscribe {
    private static final Set<String> subscribePackage = new HashSet<String>();

    public static final PackageSubscribe SUBSCRIBE = new PackageSubscribe();

    public static PackageSubscribe getInstance(){
        return SUBSCRIBE;
    }

    public Set<String> getSubscribePackage() {
        return subscribePackage;
    }
}
