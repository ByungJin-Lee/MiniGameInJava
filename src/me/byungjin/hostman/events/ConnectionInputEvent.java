package me.byungjin.hostman.events;

public interface ConnectionInputEvent {
	void dispatch(Object source, String data);
}
