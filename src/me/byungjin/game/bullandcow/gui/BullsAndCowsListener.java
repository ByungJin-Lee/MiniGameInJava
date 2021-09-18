package me.byungjin.game.bullandcow.gui;

import javax.swing.event.MouseInputAdapter;

import me.byungjin.game.bullandcow.BullsAndCows;
import me.byungjin.network.Agent;

public class BullsAndCowsListener extends MouseInputAdapter {
	private BullsAndCows bac;
	
	public BullsAndCowsListener(Agent agent) {
		bac = new BullsAndCows(agent);
	}
}
