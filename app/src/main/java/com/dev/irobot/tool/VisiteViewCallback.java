package com.dev.irobot.tool;

import android.view.View;

/**
 * Created by Jacky on 2016/4/17.
 */
public interface VisiteViewCallback {
    void onBegin();

    void onVisite(View view);

    void onEnded();
}
