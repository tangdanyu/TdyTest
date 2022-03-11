package com.example.tdytest.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;

import com.chad.library.BuildConfig;

import org.opencv.android.Utils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DeviceUtil {
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                if ("SUPPORTED_32_BIT_ABIS".equals(name)) {
                    value = Arrays.toString(Build.SUPPORTED_32_BIT_ABIS);
                } else if ("SUPPORTED_64_BIT_ABIS".equals(name)) {
                    value = Arrays.toString(Build.SUPPORTED_64_BIT_ABIS);
                } else if ("SUPPORTED_ABIS".equals(name)) {
                    value = Arrays.toString(Build.SUPPORTED_ABIS);
                }
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static final int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static final int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
    public static int getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static String getDeviceInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("app版本=").append(BuildConfig.FLAVOR).append("_").append(BuildConfig.BUILD_TYPE).append("\n");
        sb.append("versionName=").append(getVersionName(context)).append("\n");
        sb.append("versionCode=").append(getVersionCode(context)).append("\n");
        sb.append("SDK值=").append(Build.VERSION.SDK_INT).append("\n");
        sb.append("android版本=").append(Build.VERSION.RELEASE).append("\n");
        sb.append("宽度=").append(getDeviceWidth(context)).append("\n");
        sb.append("高度=").append(getDeviceHeight(context)).append("\n");
        sb.append("设备类型=").append(isDevicePhone(context) ? "手机" : "平板").append("\n");
        sb.append(getMobileInfo());
        return sb.toString();
    }
    /**
     * 判断是否是平板
     *
     * @return 是否是平板
     */
    public static boolean isDevicePhone(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) < Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    /**
     * 获取应用版本号
     * @param mContext
     * @return
     */
    public static int getAppVersionCode(Context mContext) {
        try {
            PackageManager pm = mContext.getPackageManager();
            if(pm != null){
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                return pi.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取应用版本名
     * @param mContext
     * @return
     */
    public static String getAppVersionName(Context mContext) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }
}
