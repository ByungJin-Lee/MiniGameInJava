package me.byungjin.network;

/**
 * 통신 규약 문서 참조
 */
public enum PROMISE {
	LOGIN(300), REGISTER(301), LOGIN_FAIL(302), LOGIN_SUC(303), REGISTER_IS_SAME_ID(304),
	REGISTER_SUC(305), CUT_COMMU(306), NICK(307), CHAT(308), GAME(309), RANK(310), RANK_RETURN(311),
	ROOM(312), ROOM_ENTER(313), ROOM_ENTER_SUC(314), ROOM_ENTER_FAIL(315), READY(316), UNREADY(317),
	VICTORY(318), LOSE(319);
	
	
	private short value;
	private PROMISE(int val) {
		value = (short)val;
	}	
}
