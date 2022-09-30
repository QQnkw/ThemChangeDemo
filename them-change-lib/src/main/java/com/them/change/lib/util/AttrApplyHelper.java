package com.them.change.lib.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.them.change.lib.entity.AttrFactory;
import com.them.change.lib.entity.SkinAttr;

import java.util.ArrayList;
import java.util.List;

public class AttrApplyHelper {
    private static final String TAG = "AttrApplyHelper";
    private static volatile AttrApplyHelper sSkinApplyHelper;
    private static final String RES_TYPE_NAME_COLOR = "color";
    private static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    private static final String RES_TYPE_NAME_MIPMAP = "mipmap";

    private AttrApplyHelper() {
    }

    public static AttrApplyHelper getInstance() {
        if (sSkinApplyHelper == null) {
            synchronized (AttrApplyHelper.class) {
                if (sSkinApplyHelper == null) {
                    sSkinApplyHelper = new AttrApplyHelper();
                }
            }
        }
        return sSkinApplyHelper;
    }

    public void apply(View view, List<SkinAttr> attrs, Context context) {
        Log.d(TAG, "apply-->:");
        List<SkinAttr> normalAttrList = new ArrayList<SkinAttr>();
        SkinAttr textAttr = null;
        for (SkinAttr attr : attrs) {
            String attrName = attr.attrName;
            if (AttrFactory.TEXT.equals(attrName)) {
                textAttr = attr;
            } else {
                normalAttrList.add(attr);
            }
        }

        applySkin(view, normalAttrList, context);

        if (textAttr != null && view instanceof TextView) {
            applyLanguage((TextView) view, textAttr, context);
        }

        if (view instanceof TextView) {
            applyFont((TextView) view);
        }

    }

    private void applySkin(View view, List<SkinAttr> attrs, Context context) {
        Log.d(TAG, "applySkin-->");
        Resources skinResources = SkinResourcesHelper.getInstance().mSkinResources;
        if (skinResources != null) {
            Log.d(TAG, "applySkin-->skinResources");
            for (SkinAttr attr : attrs) {
                String attrName = attr.attrName;
                String attrValueRefName = attr.attrValueRefName;
                int refId = attr.attrValueRefId;
                String attrValueTypeName = attr.attrValueTypeName;
                Log.d(TAG, "applySkin-->attrName:" + attrName + "---attrValueRefName:" + attrValueRefName + "---refId:" + refId + "---attrValueTypeName:" + attrValueTypeName);
                if (AttrFactory.BACKGROUND.equals(attrName)) {
                    //背景颜色
                    if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                        int color = AttrHelper.getExternalColor(context, refId);
                        if (color != -1) {
                            Log.d(TAG, "applySkin-->BACKGROUND:RES_TYPE_NAME_COLOR");
                            view.setBackgroundColor(color);
                        }
                    } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName) || RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
                        Drawable drawable = AttrHelper.getExternalDrawable(context, refId);
                        if (drawable != null) {
                            Log.d(TAG, "applySkin-->BACKGROUND:RES_TYPE_NAME_DRAWABLE");
                            view.setBackground(drawable);
                        }
                    }
                } else if (AttrFactory.TEXT_COLOR.equals(attrName)) {
                    //文字颜色
                    ColorStateList colorStateList = AttrHelper.getExternalColorStateList(context, refId);
                    if (colorStateList != null && view instanceof TextView) {
                        Log.d(TAG, "applySkin-->TEXT_COLOR");
                        TextView tv = (TextView) view;
                        tv.setTextColor(colorStateList);
                    }
                }  else if (AttrFactory.SRC.equals(attrName)) {
                    //图片引用
                    if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                        int color = AttrHelper.getExternalColor(context, refId);
                        if (color != -1 && view instanceof ImageView) {
                            Log.d(TAG, "applySkin-->SRC:RES_TYPE_NAME_COLOR");
                            ImageView iv = (ImageView) view;
                            ColorDrawable colorDrawable = new ColorDrawable(color);
                            iv.setImageDrawable(colorDrawable);
                        }
                    } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName) || RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
                        Drawable drawable = AttrHelper.getExternalDrawable(context, refId);
                        if (drawable != null && view instanceof ImageView) {
                            Log.d(TAG, "applySkin-->SRC:RES_TYPE_NAME_DRAWABLE");
                            ImageView iv = (ImageView) view;
                            iv.setImageDrawable(drawable);
                        }
                    }
                } else {

                }
            }
        } else {
            Log.d(TAG, "applySkin-->resources");
            for (SkinAttr attr : attrs) {
                String attrName = attr.attrName;
                String attrValueRefName = attr.attrValueRefName;
                int refId = attr.attrValueRefId;
                String attrValueTypeName = attr.attrValueTypeName;
                Log.d(TAG, "applySkin-->attrName:" + attrName + "---attrValueRefName:" + attrValueRefName + "---refId:" + refId + "---attrValueTypeName:" + attrValueTypeName);
                if (AttrFactory.BACKGROUND.equals(attrName)) {
                    //背景颜色
                    view.setBackgroundResource(refId);
                } else if (AttrFactory.TEXT_COLOR.equals(attrName)) {
                    //文字颜色
                    ColorStateList colorStateList = AttrHelper.getExternalColorStateList(context, refId);
                    if (colorStateList != null && view instanceof TextView) {
                        TextView tv = (TextView) view;
                        tv.setTextColor(colorStateList);
                    }
                }  else if (AttrFactory.SRC.equals(attrName)) {
                    //图片引用
                    if (view instanceof ImageView) {
                        ImageView iv = (ImageView) view;
                        iv.setImageResource(refId);
                    }
                } else {

                }
            }
        }

    }

    private void applyFont(TextView textView) {
        Typeface fontTypeface = SkinResourcesHelper.getInstance().mTypeface;
        Typeface typeface;
        if (fontTypeface != null) {
            typeface = fontTypeface;
            Log.d(TAG, "applyFont-->fontTypeface");
        } else {
            typeface = Typeface.DEFAULT;
            Log.d(TAG, "applyFont-->DEFAULT");
        }
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }

    private void applyLanguage(TextView textView, SkinAttr attr, Context context) {
        CharSequence text = AttrHelper.getExternalString(context,attr.attrValueRefId);
        if (text != null) {
            textView.setText(text);
        }
    }
}
