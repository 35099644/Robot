package com.dev.irobot.tool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jacky on 2016/4/10.
 */
public class Subscribe {
    private static final Set<String> subscribePackage = new HashSet<String>();

    public static final Subscribe SUBSCRIBE = new Subscribe();

    public static Subscribe getInstance(){
        return SUBSCRIBE;
    }

    public Set<String> getSubscribePackage() {
        return subscribePackage;
    }
}
