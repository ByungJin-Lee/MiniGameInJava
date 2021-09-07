package me.byungjin.hostman;

import java.util.StringTokenizer;

import me.byungjin.hostman.events.ConnectionInputEvent;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;

public class DefaultHostInputListener implements ConnectionInputEvent{

	@Override
	public void dispatch(Object source, String data) {
		ClientMan man = (ClientMan)source;
		StringTokenizer tokens = new StringTokenizer(data);
		
		String tag = tokens.nextToken();
		
		switch(Short.parseShort(tag)) {
		case ENVIRONMENT.CHAT:
			String nick = tokens.nextToken();
			//TODO
			SystemManager.message(nick + " : " + data.substring(tag.length() + nick.length() + 2));
			break;
		}
	}

}
