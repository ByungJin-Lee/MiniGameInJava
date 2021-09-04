package me.byungjin.hostman.events;

import me.byungjin.hostman.User;

public interface UserEnterHostEvent {
	void dispatch(User source);
}
