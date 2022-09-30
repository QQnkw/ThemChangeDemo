package com.them.change.lib.loader;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import com.them.change.lib.config.SkinConfig;
import com.them.change.lib.entity.AttrFactory;
import com.them.change.lib.entity.DynamicAttr;
import com.them.change.lib.entity.SkinAttr;
import com.them.change.lib.entity.SkinItem;

import java.util.ArrayList;
import java.util.List;

public class SkinInflaterFactory implements Factory {
    private static final String TAG = "SkinInflaterFactory";

    private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // if this is NOT enable to be skined , simplly skip it
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        /*int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            Log.d(TAG, "onCreateView-->name:" + name + "---attributeName:" + attributeName + "---value:" + value);
        }*/
        Log.d(TAG, "onCreateView-->name:" + name + "---isSkinEnable:" + isSkinEnable);
        if (!isSkinEnable) {
            return null;
        }

        View view = createView(context, name, attrs);
        Log.d(TAG, "onCreateView-->name:" + name + "---view is null:" + (view == null));
        if (view == null) {
            return null;
        }

        parseSkinAttr(context, attrs, view);

        return view;
    }

    /**
     * @param context
     * @param name    The full name of the class to be instantiated.
     * @param attrs   The XML attributes supplied for this instance.
     * @return View The newly instantiated view, or null.
     */
    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
        } catch (Exception e) {
            view = null;
        }
        return view;
    }

    /**
     * Collect skin able tag such as background , textColor and so on
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }

            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!viewAttrs.isEmpty()) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;

            mSkinItems.add(skinItem);


            if (SkinManager.getInstance().isExternalAssets) {
                Log.d(TAG, "applySkin-->context:" + context);
                skinItem.apply(context);
            }
        }
    }

    public void applySkin() {
        if (mSkinItems.isEmpty()) {
            return;
        }
        Log.d(TAG, "applySkin-->mSkinItems大小:" + mSkinItems.size());
        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue;
            }
            Context context = si.view.getContext();
            Log.d(TAG, "applySkin-->view:" + si.view);
            Log.d(TAG, "applySkin-->context:" + context);
            si.apply(context);
        }
    }

    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }

        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        viewAttrs.add(mSkinAttr);
        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void addSkinView(SkinItem item) {
        mSkinItems.add(item);
    }

    public void clean() {
        if (mSkinItems != null) {
            mSkinItems.clear();
            mSkinItems = null;
        }
    }
}
