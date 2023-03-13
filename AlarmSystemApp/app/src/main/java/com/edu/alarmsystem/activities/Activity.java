package com.edu.alarmsystem.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.alarmsystem.App;
import com.edu.alarmsystem.utils.AlertsHelper;
import com.edu.alarmsystem.utils.PermissionHelper;

import javax.inject.Inject;

public abstract class Activity extends AppCompatActivity {

    @Inject
    AlertsHelper alertsHelper;

    @Inject
    PermissionHelper permissionHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getApplicationContext()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }
}

