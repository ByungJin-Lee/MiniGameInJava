package me.byungjin.hostman;

import java.util.StringTokenizer;

import me.byungjin.hostman.events.ConnectionInputEvent;
import me.byungjin.manager.ENVIRONMENT;

public class DefaultUserInputListener implements ConnectionInputEvent {
	private HostMan host;
	
	public DefaultUserInputListener(HostMan host) {
		this.host = host;
	}
	
	@Override
	public void dispatch(Object source, String data) {
		User user = (User)source;		
		
		StringTokenizer tokens = new StringTokenizer(data);
		String tag = tokens.nextToken();
		
		switch(Short.parseShort(tag)) {
		case ENVIRONMENT.CHAT:
			if(user.getNick() != null)
				host.sendExceptThisUser(user, ENVIRONMENT.CHAT, user.getNick() + " " + data.substring(tag.length() + 1));
			break;
		case ENVIRONMENT.NICK:
			user.setNick(tokens.nextToken());
			break;
		}
	}
}
