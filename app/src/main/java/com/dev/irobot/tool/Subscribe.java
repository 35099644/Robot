package com.dev.irobot.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky on 2016/4/10.
 */
public class Subscribe {
    private static final List<String> subscribePackage = new ArrayList<String>();

    public static final Subscribe SUBSCRIBE = new Subscribe();

    public static Subscribe getInstance(){
        return SUBSCRIBE;
    }

    public List<String> getSubscribePackage() {
        return subscribePackage;
    }
}
