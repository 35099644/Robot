package com.dev.irobot.wechat.event;

import com.dev.irobot.wechat.WechatHookMethodHandler;

import java.util.List;

/**
 * Created by Jacky on 2016/4/17.
 */
public class DumpEvent {
    private String dump;
    private List<WechatHookMethodHandler.IdEntry> entrys;
    public DumpEvent(String dump, List<WechatHookMethodHandler.IdEntry> entrys) {
        this.dump = dump;
        this.entrys = entrys;
    }

    public String getDump() {
        return dump;
    }

    public List<WechatHookMethodHandler.IdEntry> getEntrys() {
        return entrys;
    }
}
