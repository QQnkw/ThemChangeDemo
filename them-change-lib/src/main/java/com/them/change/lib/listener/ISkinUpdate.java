package com.them.change.lib.listener;

import com.them.change.lib.util.SkinUpdateType;

public interface ISkinUpdate {
	void onThemeUpdate(@SkinUpdateType String type);
}
