package com.them.change.lib.listener;

import android.view.View;
import com.them.change.lib.entity.DynamicAttr;
import java.util.List;

public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
