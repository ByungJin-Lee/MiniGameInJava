package me.byungjin.hostman;

import java.util.Scanner;

import me.byungjin.hostman.events.ConnectionInputEvent;
import me.byungjin.manager.ENVIRONMENT;

public class HostTest {
	private Scanner scn;
	
	public HostTest() throws Exception{
		scn = new Scanner(System.in);
		start();
	}
	
	public void start() {
		System.out.println("Host - 0 \nClient - 1 > ");
		
		int sel = scn.nextInt();
		
		if(sel == 0)
			runHost();
		else
			runClient();
			
	}
	public void runHost() {
		HostMan host;
		try {
			host = new HostMan();			
			host.start();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	public void runClient() {
		try {
			ClientMan client = new ClientMan("localhost");
			client.start();
			client.send((short)1, "hh");
			client.cut();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
