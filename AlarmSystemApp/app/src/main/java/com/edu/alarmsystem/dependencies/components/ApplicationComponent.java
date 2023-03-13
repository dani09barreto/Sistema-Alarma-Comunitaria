package com.edu.alarmsystem.dependencies.components;

import com.edu.alarmsystem.activities.Activity;
import com.edu.alarmsystem.activities.HomeFragment;
import com.edu.alarmsystem.dependencies.modules.AlertsModule;
import com.edu.alarmsystem.dependencies.modules.PermissionModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AlertsModule.class, PermissionModule.class})
public interface ApplicationComponent {
    void inject(Activity activity);
}
