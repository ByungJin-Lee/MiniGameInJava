package me.byungjin.hostman.events;

import me.byungjin.hostman.HostMan;

public interface HostOpenServEvent {
	void dispatch(HostMan source);
}
