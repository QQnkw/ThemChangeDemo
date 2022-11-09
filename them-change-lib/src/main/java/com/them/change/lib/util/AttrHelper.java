package com.them.change.lib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.Arrays;

public class AttrHelper {
    private static final String TAG = "AttrHelper";

    private AttrHelper() {
    }

    private static Integer getColor(Resources resources, String resName, String packageName) {
        Integer trueColor;
        try {
            int trueResId = resources.getIdentifier(resName, "color", packageName);
            trueColor = resources.getColor(trueResId);
        } catch (Exception e) {
            trueColor = null;
            Log.d(TAG, "getColor-->Exception");
        }
        return trueColor;
    }

    public static int getExternalColor(Context context, int sourceResId) {
        Integer color = -1;
        try {
            //R.color.white
            //type是color
//            String type = context.getResources().getResourceTypeName(sourceResId);
            //name是white
            String name = context.getResources().getResourceEntryName(sourceResId);
            Log.d(TAG, "getExternalColor-->name:" + name);
            if (SkinResourcesHelper.getInstance().mSkinResources != null) {
                color = getColor(SkinResourcesHelper.getInstance().mSkinResources, name, SkinResourcesHelper.getInstance().mSkinPackageName);
                Log.d(TAG, "getExternalColor-->mSkinResources:color is " + color);
            }
            if (color == null) {
                color = context.getResources().getColor(sourceResId);
                Log.d(TAG, "getExternalColor-->sourceResId:color is " + color);
            }
        } catch (Exception e) {
            Log.d(TAG, "getExternalColor-->Exception");
        }
        return color == null ? -1 : color;
    }

    /*------------------------------------------------------------------*/
    private static ColorStateList getColorStateList(Resources resources, String resName, String packageName) {
        ColorStateList trueColorList;
        try {
            int trueResId = resources.getIdentifier(resName, "color", packageName);
            trueColorList = resources.getColorStateList(trueResId);
        } catch (Exception e) {
            trueColorList = null;
            Log.d(TAG, "getColorStateList-->Exception");
        }
        return trueColorList;
    }

    public static ColorStateList getExternalColorStateList(Context context, int sourceResId) {
        ColorStateList colorList = null;
        try {
            //R.color.white
            //type是color
//            String type = context.getResources().getResourceTypeName(sourceResId);
            //name是white
            String name = context.getResources().getResourceEntryName(sourceResId);
            Log.d(TAG, "getExternalColorStateList-->name:" + name);
            if (SkinResourcesHelper.getInstance().mSkinResources != null) {
                colorList = getColorStateList(SkinResourcesHelper.getInstance().mSkinResources, name, SkinResourcesHelper.getInstance().mSkinPackageName);
                Log.d(TAG, "getExternalColorStateList-->mSkinResources:color is " + colorList);
            }
            if (colorList == null) {
                colorList = context.getResources().getColorStateList(sourceResId);
                Log.d(TAG, "getExternalColorStateList-->sourceResId:color is " + colorList);
            }
        } catch (Exception e) {
            Log.d(TAG, "getExternalColorStateList-->Exception");
        }
        return colorList;
    }

    /*----------------------------------*/
    private static CharSequence getString(Resources resources, String resName, String packageName) {
        CharSequence text;
        try {
            int trueResId = resources.getIdentifier(resName, "string", packageName);
            text = resources.getText(trueResId);
        } catch (Exception e) {
            text = null;
            Log.d(TAG, "getString-->Exception");
        }
        return text;
    }

    public static CharSequence getExternalString(Context context, int sourceResId) {
        CharSequence str = null;
        try {
            //R.color.white
            //type是color
//            String type = context.getResources().getResourceTypeName(sourceResId);
            //name是white
            String name = context.getResources().getResourceEntryName(sourceResId);
            Log.d(TAG, "getExternalString-->name:" + name);
            if (SkinResourcesHelper.getInstance().mLanguageResources != null) {
                str = getString(SkinResourcesHelper.getInstance().mLanguageResources, name, SkinResourcesHelper.getInstance().mLanguagePackageName);
                Log.d(TAG, "getExternalString-->mLanguageResources:str:" + str);
            }
            if (str == null && SkinResourcesHelper.getInstance().mSkinResources != null) {
                str = getString(SkinResourcesHelper.getInstance().mSkinResources, name, SkinResourcesHelper.getInstance().mSkinPackageName);
                Log.d(TAG, "getExternalString-->mSkinResources:str:" + str);
            }
            if (str == null) {
                str = context.getResources().getString(sourceResId);
                Log.d(TAG, "getExternalString-->sourceResId:str:" + str);
            }
        } catch (Exception e) {
            Log.d(TAG, "getExternalString-->Exception");
        }
        return str;
    }

    /*---------------------------*/
    private static String[] getStringArray(Resources resources, String arrayName, String packageName) {
        String[] arr;
        try {
            int trueResId = resources.getIdentifier(arrayName, "array", packageName);
            arr = resources.getStringArray(trueResId);
        } catch (Exception exception) {
            Log.d(TAG, "getStringArray-->Exception");
            arr = null;
        }
        return arr;
    }

    public static String[] getExternalStringArray(Context context, int sourceResId) {
        String[] strArr = null;
        try {
            //R.color.white
            //type是color
//            String type = context.getResources().getResourceTypeName(sourceResId);
            //name是white
            String name = context.getResources().getResourceEntryName(sourceResId);
            Log.d(TAG, "getExternalStringArray-->name:" + name);
            if (SkinResourcesHelper.getInstance().mLanguageResources != null) {
                strArr = getStringArray(SkinResourcesHelper.getInstance().mLanguageResources, name, SkinResourcesHelper.getInstance().mLanguagePackageName);
                Log.d(TAG, "getExternalStringArray-->mLanguageResources:strArr:" + Arrays.toString(strArr));
            }
            if (strArr == null && SkinResourcesHelper.getInstance().mSkinResources != null) {
                strArr = getStringArray(SkinResourcesHelper.getInstance().mSkinResources, name, SkinResourcesHelper.getInstance().mSkinPackageName);
                Log.d(TAG, "getExternalStringArray-->mSkinResources:strArr:" + Arrays.toString(strArr));
            }
            if (strArr == null) {
                strArr = context.getResources().getStringArray(sourceResId);
                Log.d(TAG, "getExternalStringArray-->sourceResId:strArr:" + Arrays.toString(strArr));
            }
        } catch (Exception e) {
            Log.d(TAG, "getExternalStringArray-->Exception");
        }
        return strArr;
    }

    /*----------------------------------*/
    private static Drawable getDrawable(Resources resources, String resName, String packageName, String typeName) {
        Drawable trueDrawable;
        try {
            int trueResId = resources.getIdentifier(resName, typeName, packageName);
            trueDrawable = resources.getDrawable(trueResId);
        } catch (Exception e) {
            trueDrawable = null;
            Log.d(TAG, "getDrawable-->Exception");
        }

        return trueDrawable;
    }

    public static Drawable getExternalDrawable(Context context, int sourceResId) {
        Drawable drawable = null;
        try {
            //R.color.white
            //type是color
            String type = context.getResources().getResourceTypeName(sourceResId);
            //name是white
            String name = context.getResources().getResourceEntryName(sourceResId);
            Log.d(TAG, "getExternalDrawable-->name:" + name + "---type:" + type);
            if (SkinResourcesHelper.getInstance().mSkinResources != null) {
                drawable = getDrawable(SkinResourcesHelper.getInstance().mSkinResources, name, SkinResourcesHelper.getInstance().mSkinPackageName, type);
                Log.d(TAG, "getExternalDrawable-->mSkinResources:drawable is null " + (drawable == null));
            }
            if (drawable == null) {
                drawable = context.getResources().getDrawable(sourceResId);
                Log.d(TAG, "getExternalDrawable-->sourceResId:drawable is null " + (drawable == null));
            }
        } catch (Exception e) {
            Log.d(TAG, "getExternalDrawable-->Exception");
        }
        return drawable;
    }

    public static Drawable getExternalAppIcon(ApplicationInfo applicationInfo, PackageManager packageManager) {
        Drawable drawable = null;
        try {
            String packageName = applicationInfo.packageName;
            Log.d(TAG, "getExternalAppIcon-->mSkinResources:packageName:" + packageName);
            String[] arr = packageName.split("\\.");
            Log.d(TAG, "getExternalAppIcon-->arr:" + Arrays.toString(arr));
            int index = arr.length - 1;
            Log.d(TAG, "getExternalAppIcon-->index:" + index);
            String iconName = arr[index];
            Log.d(TAG, "getExternalAppIcon-->iconName:" + iconName);
            if (SkinResourcesHelper.getInstance().mSkinResources != null) {
                drawable = getDrawable(SkinResourcesHelper.getInstance().mSkinResources, iconName, SkinResourcesHelper.getInstance().mSkinPackageName, "drawable");
                Log.d(TAG, "getExternalAppIcon-->mSkinResources:drawable is null " + (drawable == null));
            }
            if (drawable == null) {
                drawable = applicationInfo.loadIcon(packageManager);
                Log.d(TAG, "getExternalAppIcon-->source:drawable is null " + (drawable == null));
            }
        } catch (Exception e) {
            Log.d(TAG, "getExternalAppIcon-->Exception", e);
        }
        return drawable;
    }
}
