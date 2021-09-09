package me.byungjin.telecommnuication.event;

public interface DataComeInEvent {	
	void dispatch(Object source, String data);
}
