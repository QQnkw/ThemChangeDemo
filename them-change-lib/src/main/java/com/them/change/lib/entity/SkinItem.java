package com.them.change.lib.entity;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.them.change.lib.util.AttrApplyHelper;

import java.util.ArrayList;
import java.util.List;

public class SkinItem {
	private static final String TAG = "SkinItem";
	public View view;
	
	public List<SkinAttr> attrs;

	public SkinItem(){
		attrs = new ArrayList<SkinAttr>();
	}
	
	public void apply(Context context){
		Log.d(TAG, "apply-->:");
		if(attrs.isEmpty()){
			return;
		}
		AttrApplyHelper.getInstance().apply(view,attrs,context);
	}
	
	public void clean(){
		if(attrs.isEmpty()){
			return;
		}
		for(SkinAttr at : attrs){
			at = null;
		}
	}

	@Override
	public String toString() {
		return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
	}
}
