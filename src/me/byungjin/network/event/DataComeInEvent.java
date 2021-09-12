package me.byungjin.network.event;

public interface DataComeInEvent {	
	void dispatch(Object source, String data);
}
