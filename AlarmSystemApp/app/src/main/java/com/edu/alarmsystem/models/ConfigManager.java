package com.edu.alarmsystem.models;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE_NAME = "config.properties";
    private static Properties properties;

    public static void initialize(Context context) {
        properties = new Properties();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(CONFIG_FILE_NAME);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServerIP() {
        return properties.getProperty("IPSERVER");
    }
}
