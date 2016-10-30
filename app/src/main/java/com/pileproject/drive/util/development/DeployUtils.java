/*
 * Copyright (C) 2011-2015 PILE Project, Inc. <dev@pileproject.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pileproject.drive.util.development;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

/**
 * A class for checking building environments, devices, etc.
 */
public class DeployUtils {

    private static int isOnEmulatorScore = -1;

    private static final int SCORE_MAYBE_ON_EMULATOR = 3;

    private DeployUtils() {
        throw new AssertionError("This class cannot be instantiated");
    }

    /**
     * Return true if the device is emulator.
     */
    public static boolean isOnEmulator() {

        if (isOnEmulatorScore != -1) {
            return isOnEmulatorScore >= SCORE_MAYBE_ON_EMULATOR;
        }

        int score = 0;

        if(Build.PRODUCT.equals("sdk") ||
                Build.PRODUCT.equals("google_sdk") ||
                Build.PRODUCT.equals("sdk_x86") ||
                Build.PRODUCT.equals("vbox86p")) {
            score ++;
        }

        if(Build.MANUFACTURER.equals("unknown") ||
                Build.MANUFACTURER.equals("Genymotion")) {
            score ++;
        }

        if(Build.BRAND.equals("generic") ||
                Build.BRAND.equals("generic_x86")) {
            score ++;
        }

        if(Build.DEVICE.equals("generic") ||
                Build.DEVICE.equals("generic_x86") ||
                Build.DEVICE.equals("vbox86p")) {
            score ++;
        }

        if(Build.MODEL.equals("sdk") ||
                Build.MODEL.equals("google_sdk") ||
                Build.MODEL.equals("Android SDK built for x86")) {
            score ++;
        }

        if(Build.HARDWARE.equals("goldfish") ||
                Build.HARDWARE.equals("vbox86")) {
            score ++;
        }

        if(Build.FINGERPRINT.contains("generic/sdk/generic") ||
                Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") ||
                Build.FINGERPRINT.contains("generic/google_sdk/generic") ||
                Build.FINGERPRINT.contains("generic/vbox86p/vbox86p")) {
            score ++;
        }

        isOnEmulatorScore = score;

        return isOnEmulatorScore >= SCORE_MAYBE_ON_EMULATOR;
    }

    /**
     * Return true if the building configuration is Debug.
     */
    public static boolean isDebugMode(Context context) {
        PackageManager manager = context.getPackageManager();
        ApplicationInfo info = null;

        try {
            info = manager.getApplicationInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            return false;
        }

        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE;

    }

    /**
     * Return true if the building configuration is Release.
     */
    public static boolean isReleaseMode(Context context) {
        return !isDebugMode(context);
    }
}
