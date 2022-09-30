package com.them.change.lib.loader;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

import com.them.change.lib.config.SkinConfig;
import com.them.change.lib.listener.ILoaderListener;
import com.them.change.lib.listener.ISkinLoader;
import com.them.change.lib.listener.ISkinUpdate;
import com.them.change.lib.util.SkinResourcesHelper;
import com.them.change.lib.util.SkinUpdateType;

import java.util.ArrayList;
import java.util.List;

public class SkinManager implements ISkinLoader {
    private static final String TAG = "SkinManager";
    private static volatile SkinManager instance;
    private List<ISkinUpdate> skinObservers;
    private Context context;
    public boolean isExternalAssets = false;
    private Handler mHandler;

    public boolean isExternalAssets() {
        return isExternalSkin() || isExternalFont() || isExternalLanguage();
    }

    public boolean isExternalSkin() {
        boolean defaultSkin = SkinConfig.isDefaultSkin(context);
        return !defaultSkin;
    }

    public boolean isExternalFont() {
        boolean defaultFont = SkinConfig.isDefaultFont(context);
        return !defaultFont;
    }

    public boolean isExternalLanguage() {
        boolean defaultLanguage = SkinConfig.isDefaultLanguage(context);
        return !defaultLanguage;
    }

    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager();
                }
            }
        }
        return instance;
    }

    private SkinManager() {
    }

    public void init(Application application) {
        context = application.getBaseContext();
        mHandler = new Handler();
    }

    /*做系统应用时,监听加载主题状态
    public void addThemUpdateListener(Application application){
        application.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(SkinConfig.CUSTOM_SKIN_PATH), true, new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange) {
                if (SkinManager.getInstance().isExternalSkin()) {
                    loadSkin(SkinConfig.getCustomSkinPath(context),null);
                }else {
                    SkinResourcesHelper.getInstance().mSkinResources = null;
                    notifySkinUpdate(SkinUpdateType.SKIN);
                }
                Log.d(TAG, "CUSTOM_SKIN_PATH:" + selfChange);
            }
        });
        application.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(SkinConfig.CUSTOM_FONT_PATH), true, new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange) {
                if (SkinManager.getInstance().isExternalFont()) {
                    loadFont(SkinConfig.getCustomFontPath(context),null);
                }else {
                    SkinResourcesHelper.getInstance().mTypeface = null;
                    notifySkinUpdate(SkinUpdateType.FONT);
                }
                Log.d(TAG, "CUSTOM_FONT_PATH:" + selfChange);
            }
        });
        application.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(SkinConfig.CUSTOM_LANGUAGE_PATH), true, new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange) {
                if (SkinManager.getInstance().isExternalLanguage()) {
                    loadLanguage(SkinConfig.getCustomLanguagePath(context),null);
                }else {
                    SkinResourcesHelper.getInstance().mLanguageResources = null;
                    notifySkinUpdate(SkinUpdateType.LANGUAGE);
                }
                Log.d(TAG, "CUSTOM_LANGUAGE_PATH:" + selfChange);
            }
        });
    }*/

    public void load(){
        String skinPath = "";
        if (!SkinConfig.isDefaultSkin(context)) {
            skinPath = SkinConfig.getCustomSkinPath(context);
        }
        String fontPath = "";
        if (!SkinConfig.isDefaultFont(context)) {
            fontPath = SkinConfig.getCustomFontPath(context);
        }

        String languagePath = "";
        if (!SkinConfig.isDefaultLanguage(context)) {
            languagePath = SkinConfig.getCustomLanguagePath(context);
        }
        SkinResourcesHelper.getInstance().loadAll(context, skinPath, fontPath, languagePath);
    }

    public void loadSkin(String skinPackagePath, ILoaderListener callback) {
        SkinResourcesHelper.getInstance().loadSkin(context, skinPackagePath, callback);
    }

    public void loadFont(String fontPackagePath, ILoaderListener callback) {
        SkinResourcesHelper.getInstance().loadFont(context, fontPackagePath, callback);
    }

    public void loadLanguage(String languagePath, ILoaderListener callback) {
        SkinResourcesHelper.getInstance().loadLanguage(context, languagePath, callback);
    }

    public void resetDefaultAll() {
        SkinResourcesHelper.getInstance().mSkinResources = null;
        SkinResourcesHelper.getInstance().mTypeface = null;
        SkinResourcesHelper.getInstance().mLanguageResources = null;
        SkinConfig.saveSkinPath(context, SkinConfig.DEFAULT_SKIN);
        SkinConfig.saveFontPath(context, SkinConfig.DEFAULT_FONT);
        SkinConfig.saveLanguagePath(context, SkinConfig.DEFAULT_LANGUAGE);
        notifySkinUpdate(SkinUpdateType.ALL);
    }

    public void resetDefaultSkin() {
        SkinResourcesHelper.getInstance().mSkinResources = null;
        SkinConfig.saveSkinPath(context, SkinConfig.DEFAULT_SKIN);
        notifySkinUpdate(SkinUpdateType.SKIN);
    }

    public void resetDefaultFont() {
        SkinResourcesHelper.getInstance().mTypeface = null;
        SkinConfig.saveFontPath(context, SkinConfig.DEFAULT_FONT);
        notifySkinUpdate(SkinUpdateType.FONT);
    }

    public void resetDefaultLanguage() {
        SkinResourcesHelper.getInstance().mLanguageResources = null;
        SkinConfig.saveLanguagePath(context, SkinConfig.DEFAULT_LANGUAGE);
        notifySkinUpdate(SkinUpdateType.LANGUAGE);
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (skinObservers == null) {
            skinObservers = new ArrayList<ISkinUpdate>();
        }
        if (!skinObservers.contains(observer)) {
            skinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (skinObservers == null) return;
        if (skinObservers.contains(observer)) {
            skinObservers.remove(observer);
            if (skinObservers.isEmpty()) {
                skinObservers = null;
                mHandler.removeCallbacksAndMessages(null);
            }
        }
    }

    @Override
    public void notifySkinUpdate(@SkinUpdateType final String type) {
        if (skinObservers == null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (skinObservers == null) {
                        mHandler.postDelayed(this, 100L);
                    } else {
                        notifySkinUpdate(type);
                    }
                }
            }, 100L);
        } else {
            for (ISkinUpdate observer : skinObservers) {
                observer.onThemeUpdate(type);
            }
        }
    }
}