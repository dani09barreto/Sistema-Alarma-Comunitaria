package com.edu.alarmsystem;

import android.app.Application;

import com.edu.alarmsystem.dependencies.components.ApplicationComponent;
import com.edu.alarmsystem.dependencies.components.DaggerApplicationComponent;

import lombok.Getter;

@Getter
public class App extends Application {
    ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .build();

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
