package me.byungjin.minigame;

import java.util.Scanner;

import me.byungjin.hostman.ClientMan;
import me.byungjin.hostman.HostMan;
import me.byungjin.hostman.Man;
import me.byungjin.minigame.gui.ReadyFrame;

public class MiniGame {
	private Scanner scn;
	public static Man man = null;
	
	public MiniGame() throws Exception {		
		scn = new Scanner(System.in);
		System.out.println("H : 0 / C : 1");
		
		
		switch(scn.nextInt()) {
		case 0:
			man = new HostMan();
			break;
		case 1:
			man = new ClientMan("localhost");
			break;
		}
		man.work();
		new ReadyFrame();
	}
	
	public static void main(String[] args) {
		try {
			new MiniGame();
		}catch(Exception e) {
			
		}
	}
}
