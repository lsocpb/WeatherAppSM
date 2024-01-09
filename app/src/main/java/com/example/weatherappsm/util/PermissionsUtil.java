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
        if (context != null && allPermissionNeeded != null) {
            for (String permission : allPermissionNeeded) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void requestPermission(Context context, int requestCode, String... allPermissionNeeded) {
        if (context != null && allPermissionNeeded != null) {
            ActivityCompat.requestPermissions((Activity) context, allPermissionNeeded, requestCode);
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Sprawdź, czy requestCode odpowiada temu, który użyto w requestPermission
        // Tutaj można również sprawdzić, czy grantResults zawiera uprawnienia, które zostały przyznane
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if ((permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) || (permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) && grantResult == PackageManager.PERMISSION_GRANTED)) {
                    // Użytkownik udzielił uprawnienia
                    // Tutaj możesz wykonać odpowiednie akcje po uzyskaniu uprawnienia
                } else {
                    // Użytkownik odmówił uprawnienia lub wystąpił błąd
                    // Tutaj możesz obsłużyć sytuację, w której uprawnienie nie zostało udzielone
                }
            }
        }
    }*/
}
