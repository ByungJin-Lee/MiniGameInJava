package me.byungjin.hostman.events;

import me.byungjin.hostman.User;

public interface UserConnectionEndEvent {
	void dispatch(User source);
}
