package me.byungjin.minigame;

import me.byungjin.manager.Environment;
import me.byungjin.manager.SystemManager;

public class MiniGame {	
	
	public MiniGame() throws Exception {		
		
	}
	
	public static void main(String[] args) {
		try {
			Environment.getEnvironment(true);
			Environment.show();
			Environment.save();
		}catch(Exception e) {
			SystemManager.catchException((short)0, e);
		}
	}
}
