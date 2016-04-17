package com.dev.irobot.event;

import com.dev.irobot.tool.IdEntry;

import java.util.List;

/**
 * Created by Jacky on 2016/4/17.
 */
public class DumpViewEvent {
    private String dump;
    private List<IdEntry> entrys;
    public DumpViewEvent(String dump, List<IdEntry> entrys) {
        this.dump = dump;
        this.entrys = entrys;
    }

    public String getDump() {
        return dump;
    }

    public List<IdEntry> getEntrys() {
        return entrys;
    }
}
