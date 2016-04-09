package com.dev.irobot.handler;

import com.dev.irobot.RobotProcesser;

public class HandlerManager {

    private volatile static RobotProcesser robotProcesser = new RobotProcesser();
    public static RobotProcesser getProcesser(){
        return robotProcesser;
    }
}
