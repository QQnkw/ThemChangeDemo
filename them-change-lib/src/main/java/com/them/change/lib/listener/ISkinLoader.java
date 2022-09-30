package com.them.change.lib.listener;

public interface ISkinLoader {
	void attach(ISkinUpdate observer);
	void detach(ISkinUpdate observer);
	void notifySkinUpdate(String type);
}
