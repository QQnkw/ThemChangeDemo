package com.them.change.lib.entity;


public class AttrFactory {
	
	public static final String BACKGROUND = "background";
	public static final String TEXT_COLOR = "textColor";
	public static final String SRC = "src";
	public static final String TEXT = "text";

	public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName){
		SkinAttr mSkinAttr = new SkinAttr();
		mSkinAttr.attrName = attrName;
		mSkinAttr.attrValueRefId = attrValueRefId;
		mSkinAttr.attrValueRefName = attrValueRefName;
		mSkinAttr.attrValueTypeName = typeName;
		return mSkinAttr;
	}

	public static boolean isSupportedAttr(String attrName){
		if(BACKGROUND.equals(attrName)){ 
			return true;
		}
		if(TEXT_COLOR.equals(attrName)){ 
			return true;
		}
		if(SRC.equals(attrName)){
			return true;
		}
		if(TEXT.equals(attrName)){
			return true;
		}
		return false;
	}
}
