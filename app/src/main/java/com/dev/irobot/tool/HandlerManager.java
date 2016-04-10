package com.dev.irobot.tool;

import com.dev.irobot.handler.RobotEventProcesser;

public class HandlerManager {

    private static final RobotEventProcesser EVENT_PROCESSER = new RobotEventProcesser();
    public static RobotEventProcesser getEventProcesser(){
        return EVENT_PROCESSER;
    }

}
