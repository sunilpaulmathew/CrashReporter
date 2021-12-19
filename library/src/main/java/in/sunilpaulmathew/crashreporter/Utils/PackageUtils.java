package in.sunilpaulmathew.crashreporter.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on December 20, 2021
 */
public class PackageUtils {

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(getPackageName(context), PackageManager.GET_META_DATA);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static CharSequence getAppName(Context context) {
        return context.getPackageManager().getApplicationLabel(getApplicationInfo(context));
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageArchiveInfo(getSourceDir(context), 0);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getSourceDir(Context context) {
        return Objects.requireNonNull(getApplicationInfo(context)).sourceDir;
    }

    public static String getVersionName(Context context) {
        if (getPackageInfo(context) != null) {
            return Objects.requireNonNull(getPackageInfo(context)).versionName;
        } else {
            return null;
        }
    }

}