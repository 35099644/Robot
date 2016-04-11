package com.dev.irobot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dev.irobot.R;
import com.dev.irobot.subscribe.HookMethodHandlerSubscriber;
import com.dev.irobot.wechat.WechatHookMethodHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HookMethodHandlerSubscriber.getInstance().getHookMethodHandlers().put("com.tencent.mm", new WechatHookMethodHandler());
    }
}
