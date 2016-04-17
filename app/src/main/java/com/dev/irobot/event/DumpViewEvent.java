package com.dev.irobot.event;

import com.dev.irobot.tool.IdEntry;
import com.dev.irobot.tool.ViewDump;

import java.util.List;

/**
 * Created by Jacky on 2016/4/17.
 */
public class DumpViewEvent {
    private ViewDump viewDump;
    private List<IdEntry> entrys;
    public DumpViewEvent(ViewDump dump, List<IdEntry> entrys) {
        this.viewDump = dump;
        this.entrys = entrys;
    }

    public ViewDump getViewDump() {
        return viewDump;
    }

    public List<IdEntry> getEntrys() {
        return entrys;
    }
}
