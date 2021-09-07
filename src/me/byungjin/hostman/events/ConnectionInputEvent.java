package me.byungjin.hostman.events;

import me.byungjin.hostman.HostMan;

public interface ConnectionInputEvent {
	void dispatch(Object source, String data);
}
