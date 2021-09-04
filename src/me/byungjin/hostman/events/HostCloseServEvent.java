package me.byungjin.hostman.events;

import me.byungjin.hostman.HostMan;

public interface HostCloseServEvent {
	void dispatch(HostMan source);
}
