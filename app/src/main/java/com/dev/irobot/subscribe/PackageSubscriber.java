package com.dev.irobot.subscribe;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Jacky on 2016/4/10.
 */
public final class PackageSubscriber {
    private static final Set<String> subscribePackage = new CopyOnWriteArraySet<String>();

    public static final PackageSubscriber SUBSCRIBER = new PackageSubscriber();

    public static PackageSubscriber getInstance(){
        return SUBSCRIBER;
    }

    public Set<String> getSubscribePackage() {
        return subscribePackage;
    }
}
