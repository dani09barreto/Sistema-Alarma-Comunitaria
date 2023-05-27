package com.edu.alarmsystem;

import android.app.Application;

import com.edu.alarmsystem.dependencies.components.ApplicationComponent;
import com.edu.alarmsystem.dependencies.components.DaggerApplicationComponent;
import com.edu.alarmsystem.models.ConfigManager;

import lombok.Getter;

@Getter
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigManager.initialize(getApplicationContext());
    }

    ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .build();

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }



}
