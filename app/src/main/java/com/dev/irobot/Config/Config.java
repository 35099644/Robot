package com.dev.irobot.Config;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.dev.irobot.ContextHolder;

/**
 * Created by Jacky on 2016/4/17.
 */
public class Config {
    private static final Config config = new Config();

    private SharedPreferences sharedPreferences;

    private static final Object LOCK = new Object();
    private Config(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ContextHolder.getInstance().getContext());
    }

    public static Config getConfig(){
        return config;
    }

    public boolean putString(String key, String value){
        synchronized (LOCK){
            return sharedPreferences.edit().putString(key, value).commit();
        }
    }

    public String getString(String key, String defaultValue){
        synchronized (LOCK){
            return sharedPreferences.getString(key, defaultValue);
        }
    }
}
