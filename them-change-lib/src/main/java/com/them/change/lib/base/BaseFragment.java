package com.them.change.lib.base;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import com.them.change.lib.entity.DynamicAttr;
import com.them.change.lib.listener.IDynamicNewView;


import java.util.List;

public class BaseFragment extends Fragment implements IDynamicNewView {
	
	private IDynamicNewView mIDynamicNewView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mIDynamicNewView = (IDynamicNewView)activity;
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("IDynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(view, pDAttrs);
		}
	}
}
