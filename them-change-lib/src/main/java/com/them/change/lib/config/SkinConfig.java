package com.them.change.lib.config;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.them.change.lib.util.PreferencesUtils;


public class SkinConfig {
    public static final String NAMESPACE = "http://schemas.android.com/android/skin";
    //自定义属性change_skin放到framework层时,需要用下面的
    //    public static final String NAMESPACE = "http://schemas.android.com/apk/res/android";
    public static final String ATTR_SKIN_ENABLE = "change_skin";
    public static final String CUSTOM_SKIN_PATH = "custom_skin_path";
    public static final String DEFAULT_SKIN = "skin_default";
    public static final String CUSTOM_FONT_PATH = "custom_font_path";
    public static final String DEFAULT_FONT = "font_default";
    public static final String CUSTOM_LANGUAGE_PATH = "custom_language_path";
    public static final String DEFAULT_LANGUAGE = "language_default";

    /*做系统应用时状态同步到其它应用时用到
    public static String getCustomSkinPath(Context context) {
        String path = Settings.System.getString(context.getContentResolver(), CUSTOM_SKIN_PATH);
        Log.d("SkinConfig","getCustomSkinPath-->path:"+path);
        if (path == null || path.isEmpty()) {
            path = DEFAULT_SKIN;
        }
        return path;
    }

    public static void saveSkinPath(Context context, String path) {
        Settings.System.putString(context.getContentResolver(), CUSTOM_SKIN_PATH, path);
    }

    public static boolean isDefaultSkin(Context context) {
        return DEFAULT_SKIN.equals(getCustomSkinPath(context));
    }*/

    public static String getCustomSkinPath(Context context) {
        String path = PreferencesUtils.getString(context, CUSTOM_SKIN_PATH, DEFAULT_SKIN);
        Log.d("SkinConfig", "getCustomSkinPath-->path:" + path);
        return path;
    }

    public static void saveSkinPath(Context context, String path) {
        PreferencesUtils.putString(context, CUSTOM_SKIN_PATH, path);
    }

    public static boolean isDefaultSkin(Context context) {
        return DEFAULT_SKIN.equals(getCustomSkinPath(context));
    }

    /*-----------------------------*/
    /*做系统应用时状态同步到其它应用时用到
    public static String getCustomFontPath(Context context) {
        String path = Settings.System.getString(context.getContentResolver(), CUSTOM_FONT_PATH);
        Log.d("SkinConfig","getCustomFontPath-->path:"+path);
        if (path == null || path.isEmpty()) {
            path = DEFAULT_FONT;
        }
        return path;
    }

    public static void saveFontPath(Context context, String path) {
        Settings.System.putString(context.getContentResolver(), CUSTOM_FONT_PATH, path);
    }

    public static boolean isDefaultFont(Context context) {
        return DEFAULT_FONT.equals(getCustomFontPath(context));
    }*/

    public static String getCustomFontPath(Context context) {
        String path = PreferencesUtils.getString(context, CUSTOM_FONT_PATH, DEFAULT_FONT);
        Log.d("SkinConfig", "getCustomFontPath-->path:" + path);
        return path;
    }

    public static void saveFontPath(Context context, String path) {
        PreferencesUtils.putString(context, CUSTOM_FONT_PATH, path);
    }

    public static boolean isDefaultFont(Context context) {
        return DEFAULT_FONT.equals(getCustomFontPath(context));
    }

    /*-----------------------------*/
    /*做系统应用时状态同步到其它应用时用到
    public static String getCustomLanguagePath(Context context) {
        String path = Settings.System.getString(context.getContentResolver(), CUSTOM_LANGUAGE_PATH);
        Log.d("SkinConfig","getCustomLanguagePath-->path:"+path);
        if (path == null || path.isEmpty()) {
            path = DEFAULT_LANGUAGE;
        }
        return path;
    }

    public static void saveLanguagePath(Context context, String path) {
        Settings.System.putString(context.getContentResolver(), CUSTOM_LANGUAGE_PATH, path);
    }

    public static boolean isDefaultLanguage(Context context) {
        return DEFAULT_LANGUAGE.equals(getCustomLanguagePath(context));
    }*/

    public static String getCustomLanguagePath(Context context) {
        String path = PreferencesUtils.getString(context, CUSTOM_LANGUAGE_PATH, DEFAULT_LANGUAGE);
        Log.d("SkinConfig", "getCustomLanguagePath-->path:" + path);
        return path;
    }

    public static void saveLanguagePath(Context context, String path) {
        PreferencesUtils.putString(context, CUSTOM_LANGUAGE_PATH, path);
    }

    public static boolean isDefaultLanguage(Context context) {
        return DEFAULT_LANGUAGE.equals(getCustomLanguagePath(context));
    }
}
