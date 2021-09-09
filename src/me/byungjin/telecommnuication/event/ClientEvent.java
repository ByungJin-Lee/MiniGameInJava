package me.byungjin.telecommnuication.event;

import me.byungjin.telecommnuication.Client;

public interface ClientEvent {
	void dispatch(Client source);
}
