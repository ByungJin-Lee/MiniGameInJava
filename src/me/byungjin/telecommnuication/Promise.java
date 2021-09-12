package me.byungjin.telecommnuication;

/**
 * 통신 규약 문서 참조
 */
public class PROMISE {	
	public static final short LOGIN = 300;
	public static final short REGISTER = 301;
	public static final short LOGIN_FAIL = 302;	
	public static final short LOGIN_SUC = 303;
	public static final short REGISTER_IS_SAME_ID = 304;
	public static final short REGISTER_SUC = 305;
	public static final short CUT_COMMU = 306;
	public static final short NICK = 307;
	public static final short CHAT = 308;
	/**
	 * 해당 상수는 예외규약을 따름 - GAME 규약 참조
	 */
	public static final short GAME = 309;
	public static final short RANK = 310;
	public static final short RANK_RETURN = 311;
	public static final short ROOM = 312;
	public static final short ROOM_ENTER = 312;
	public static final short ROOM_ENTER_SUC = 313;
	public static final short ROOM_ENTER_FAIL = 315;
	public static final short READY = 316;
	public static final short UNREADY = 317;
	public static final short VICTORY = 318;
	public static final short LOSE = 319;
}
