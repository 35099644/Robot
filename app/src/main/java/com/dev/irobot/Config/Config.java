package com.dev.irobot.config;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Jacky on 2016/4/17.
 */
public class Config {
    private static final Config config = new Config();

    private Properties properties = new Properties();


    private File dir =new File(Environment.getExternalStorageDirectory().getPath(), "com.dev.irobot");
    private String name="config.properties";
    private Config() {
    }

    public static Config getConfig() {
        return config;
    }

    public void load() {
        try {
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir, name);
            if(!file.exists()){
                file.createNewFile();
            }

            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void save() {
        try {
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir, name);
            if(!file.exists()){
                file.createNewFile();
            }
            properties.store(new FileOutputStream(file), "hook config");
            System.out.println(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Properties getProperties() {
        return properties;
    }
}
