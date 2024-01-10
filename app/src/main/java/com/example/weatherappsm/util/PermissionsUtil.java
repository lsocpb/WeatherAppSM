package com.example.weatherappsm.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PermissionsUtil {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public static boolean hasPermission(Context context, String... allPermissionNeeded) {
        if (context == null || allPermissionNeeded == null) {
            return false;
        }

        for (String permission : allPermissionNeeded) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
