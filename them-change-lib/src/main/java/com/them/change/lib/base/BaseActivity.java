package com.them.change.lib.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.them.change.lib.entity.DynamicAttr;
import com.them.change.lib.listener.IDynamicNewView;
import com.them.change.lib.listener.ISkinUpdate;
import com.them.change.lib.loader.SkinInflaterFactory;
import com.them.change.lib.loader.SkinManager;
import com.them.change.lib.util.SkinUpdateType;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements ISkinUpdate, IDynamicNewView {
    private static final String TAG = "BaseActivity";

    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(mSkinInflaterFactory);
        SkinManager.getInstance().attach(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable) {
        isResponseOnSkinChanging = enable;
    }

    @Override
    public void onThemeUpdate(String type) {
        if (!isResponseOnSkinChanging) {
            return;
        }
        mSkinInflaterFactory.applySkin();
        if (SkinUpdateType.FONT.equals(type)) {
            onChangeFont();
        } else if (SkinUpdateType.SKIN.equals(type)) {
            onChangeSkin();
        }else if (SkinUpdateType.LANGUAGE.equals(type)) {
            onChangeLanguage();
        } else {

        }
        Log.d(TAG, "onThemeUpdate-->");
    }

    protected void onChangeLanguage() {

    }

    protected void onChangeSkin() {

    }

    protected void onChangeFont() {

    }
}
