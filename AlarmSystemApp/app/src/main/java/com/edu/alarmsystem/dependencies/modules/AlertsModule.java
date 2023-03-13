package com.edu.alarmsystem.dependencies.modules;

import com.edu.alarmsystem.utils.AlertsHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AlertsModule {
    @Singleton
    @Provides
    public AlertsHelper provideAlertHelper() {
        return new AlertsHelper();
    }
}
