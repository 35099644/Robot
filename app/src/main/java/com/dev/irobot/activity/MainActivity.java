package com.dev.irobot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dev.irobot.R;
import com.dev.irobot.WechatInterceptor;
import com.dev.irobot.subscribe.HookMethodInterceptorSubscriber;
import com.dev.irobot.subscribe.PackageSubscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageSubscriber.getInstance().getSubscribePackage().add("com.tencent.mm");
        HookMethodInterceptorSubscriber.getInstance().getSubscriHookMethodInterceptor().add(new WechatInterceptor());
    }
}
