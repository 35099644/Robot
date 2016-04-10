package com.dev.irobot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dev.irobot.R;
import com.dev.irobot.ViewEventHandler;
import com.dev.irobot.subscribe.EventHandlerSubscribe;
import com.dev.irobot.subscribe.PackageSubscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageSubscribe.getInstance().getSubscribePackage().add("com.tencent.mm");

        EventHandlerSubscribe.getInstance().getSubscribeHandlers().put(ViewEventHandler.class.getName(), new ViewEventHandler());
    }
}
