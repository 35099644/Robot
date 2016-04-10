package com.dev.irobot.tool;

import com.dev.irobot.handler.RobotProcesser;

public class HandlerManager {

    private static final RobotProcesser robotProcesser = new RobotProcesser();
    public static RobotProcesser getProcesser(){
        return robotProcesser;
    }

}
