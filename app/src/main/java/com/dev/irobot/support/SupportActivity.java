package com.dev.irobot.support;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dev.irobot.tool.Log;


public class SupportActivity extends AppCompatActivity {

    private static final String TAG = SupportActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(this.getClass().getSimpleName(), "onBackPressed");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(this.getClass().getSimpleName(), "onLowMemory");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(this.getClass().getSimpleName(), "onNewIntent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getSimpleName(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(this.getClass().getSimpleName(), "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(this.getClass().getSimpleName(), "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this.getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(this.getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(this.getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getSimpleName(), "onDestory");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(this.getClass().getSimpleName(), "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(this.getClass().getSimpleName(), "onRestoreInstanceState");
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        Log.d(this.getClass().getSimpleName(), "onRetainCustomNonConfigurationInstance");
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(this.getClass().getSimpleName(), "onConfigurationChanged");
    }

}
