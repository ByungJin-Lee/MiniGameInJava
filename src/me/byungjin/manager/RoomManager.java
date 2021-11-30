package me.byungjin.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import me.byungjin.game.Room;

public class RoomManager {
	private static Vector<Room> rooms;
	
	public static void init() {
		rooms = new Vector<Room>();
	}
	
	public static boolean add(Room room) {		
		if(!rooms.contains(room)) {
			rooms.add(room);
			SystemManager.message(ENVIRONMENT.ROOM, "Add Success :" + room.getRoomName());
			return true;
		}
		SystemManager.message(ENVIRONMENT.ROOM, "Same Room is Already Exist");
		return false;
	}
	
	public static void remove(Room room) {
		rooms.remove(room);
		SystemManager.message(ENVIRONMENT.ROOM, "Remove Success :" + room.getRoomName());
	}
	public static Room[] getRoomArray() {
		return (Room[])rooms.toArray();
	}	
	public static Vector<Room> getRoomVector(){
		return rooms;
	}
}
