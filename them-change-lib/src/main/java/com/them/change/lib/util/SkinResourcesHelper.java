package com.them.change.lib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;

import com.them.change.lib.config.SkinConfig;
import com.them.change.lib.listener.ILoaderListener;
import com.them.change.lib.loader.SkinManager;

import java.io.File;
import java.lang.reflect.Method;

public class SkinResourcesHelper {
    private static volatile SkinResourcesHelper sSkinResourcesHelper;

    private SkinResourcesHelper() {
    }

    public static SkinResourcesHelper getInstance() {
        if (sSkinResourcesHelper == null) {
            synchronized (SkinResourcesHelper.class) {
                if (sSkinResourcesHelper == null) {
                    sSkinResourcesHelper = new SkinResourcesHelper();
                }
            }
        }
        return sSkinResourcesHelper;
    }

    /*------------------加载主题包----------------------------*/
    public String mSkinPackageName;
    public Resources mSkinResources;

    public void loadSkin(final Context context, String skinPackagePath, final ILoaderListener callback) {
        new AsyncTask<String, Void, Resources>() {

            protected void onPreExecute() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Resources doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        String skinPkgPath = params[0];

                        File file = new File(skinPkgPath);
                        if (file == null || !file.exists()) {
                            return null;
                        }

                        PackageManager mPm = context.getPackageManager();
                        PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                        mSkinPackageName = mInfo.packageName;

                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, skinPkgPath);

                        Resources superRes = context.getResources();
                        Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

                        SkinConfig.saveSkinPath(context, skinPkgPath);

                        return skinResource;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Resources result) {
                mSkinResources = result;
                if (mSkinResources != null) {
                    if (callback != null) callback.onSuccess();
                    SkinManager.getInstance().isExternalAssets = true;
                    SkinManager.getInstance().notifySkinUpdate(SkinUpdateType.SKIN);
                } else {
                    if (callback != null) callback.onFailed();
                }
            }

        }.execute(skinPackagePath);
    }

    /*------------------加载字体包----------------------------*/
    public Typeface mTypeface;

    public void loadFont(final Context context, String fontPath, final ILoaderListener callback) {
        new AsyncTask<String, Void, Typeface>() {

            protected void onPreExecute() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Typeface doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        String fontPath = params[0];

                        File file = new File(fontPath);
                        if (file == null || !file.exists()) {
                            return null;
                        }
                        Typeface typeface = Typeface.createFromFile(file);
                        SkinConfig.saveFontPath(context, fontPath);
                        return typeface;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Typeface typeface) {
                mTypeface = typeface;
                if (mTypeface != null) {
                    if (callback != null) callback.onSuccess();
                    SkinManager.getInstance().isExternalAssets = true;
                    SkinManager.getInstance().notifySkinUpdate(SkinUpdateType.FONT);
                } else {
                    if (callback != null) callback.onFailed();
                }
            }

        }.execute(fontPath);
    }

    /*------------------加载多语言----------------------------*/
    public String mLanguagePackageName;
    public Resources mLanguageResources;

    public void loadLanguage(final Context context, String languagePackagePath, final ILoaderListener callback) {
        new AsyncTask<String, Void, Resources>() {

            protected void onPreExecute() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Resources doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        String languagePkgPath = params[0];

                        File file = new File(languagePkgPath);
                        if (file == null || !file.exists()) {
                            return null;
                        }

                        PackageManager mPm = context.getPackageManager();
                        PackageInfo mInfo = mPm.getPackageArchiveInfo(languagePkgPath, PackageManager.GET_ACTIVITIES);
                        mLanguagePackageName = mInfo.packageName;

                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, languagePkgPath);

                        Resources superRes = context.getResources();
                        Resources languageResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

                        SkinConfig.saveLanguagePath(context, languagePkgPath);
                        return languageResource;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Resources result) {
                mLanguageResources = result;
                if (mLanguageResources != null) {
                    if (callback != null) callback.onSuccess();
                    SkinManager.getInstance().isExternalAssets = true;
                    SkinManager.getInstance().notifySkinUpdate(SkinUpdateType.LANGUAGE);
                } else {
                    if (callback != null) callback.onFailed();
                }
            }

        }.execute(languagePackagePath);
    }

    public void loadAll(final Context context, String... path) {
        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                int count = 0;
                try {
                    if (params.length == 3) {
                        String skinPkgPath = params[0];
                        if (skinPkgPath != null && !skinPkgPath.isEmpty()) {
                            File skinFile = new File(skinPkgPath);
                            if (skinFile != null && skinFile.exists()) {
                                Log.d("SkinResourcesHelper", "loadAll-->doInBackground-->skinPkgPath:"+skinPkgPath);
                                PackageManager mPm = context.getPackageManager();
                                Resources superRes = context.getResources();
                                PackageInfo skinInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                                mSkinPackageName = skinInfo.packageName;
                                AssetManager skinAssetManager = AssetManager.class.newInstance();
                                Method addAssetPath = skinAssetManager.getClass().getMethod("addAssetPath", String.class);
                                addAssetPath.invoke(skinAssetManager, skinPkgPath);
                                mSkinResources = new Resources(skinAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                                SkinConfig.saveSkinPath(context, skinPkgPath);
                                count++;
                            }
                        }
                        String fontPkgPath = params[1];
                        if (fontPkgPath != null && !fontPkgPath.isEmpty()) {
                            File fontFile = new File(fontPkgPath);
                            if (fontFile != null && fontFile.exists()) {
                                Log.d("SkinResourcesHelper", "loadAll-->doInBackground-->fontPkgPath:"+fontPkgPath);
                                mTypeface = Typeface.createFromFile(fontFile);
                                SkinConfig.saveFontPath(context, fontPkgPath);
                                count++;
                            }
                        }
                        String languagePkgPath = params[2];
                        if (languagePkgPath != null && !languagePkgPath.isEmpty()) {
                            File languageFile = new File(languagePkgPath);
                            if (languageFile != null && languageFile.exists()) {
                                Log.d("SkinResourcesHelper", "loadAll-->doInBackground-->languagePkgPath:"+languagePkgPath);
                                PackageManager mPm = context.getPackageManager();
                                Resources superRes = context.getResources();
                                PackageInfo languageInfo = mPm.getPackageArchiveInfo(languagePkgPath, PackageManager.GET_ACTIVITIES);
                                mLanguagePackageName = languageInfo.packageName;
                                AssetManager languageAssetManager = AssetManager.class.newInstance();
                                Method addAssetPath = languageAssetManager.getClass().getMethod("addAssetPath", String.class);
                                addAssetPath.invoke(languageAssetManager, languagePkgPath);
                                mLanguageResources = new Resources(languageAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                                SkinConfig.saveLanguagePath(context, languagePkgPath);
                                count++;
                            }
                        }
                    }
                    Log.d("SkinResourcesHelper", "loadAll-->doInBackground-->");
                    return count;
                } catch (Exception e) {
                    Log.d("SkinResourcesHelper", "loadAll-->doInBackground-->Exception");
                    return count;
                }
            }

            @Override
            protected void onPostExecute(Integer count) {
                if (count > 0) {
                    Log.d("SkinResourcesHelper", "loadAll-->onPostExecute-->count:" + count);
                    SkinManager.getInstance().isExternalAssets = true;
                    SkinManager.getInstance().notifySkinUpdate(SkinUpdateType.ALL);
                }
            }
        }.execute(path);
    }
}
