package com.dev.irobot.event;

import android.view.View;

/**
 * Created by Jacky on 2016/4/17.
 */
public class ViewClickEvent {
    private final View view;
    public ViewClickEvent(View v) {
        this.view = v;
    }

    public View getView() {
        return view;
    }
}
