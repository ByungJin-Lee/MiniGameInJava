package me.byungjin.network.event;

import me.byungjin.network.Client;

public interface ClientEvent {
	void dispatch(Client source);
}
