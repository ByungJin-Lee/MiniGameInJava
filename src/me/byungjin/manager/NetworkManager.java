package me.byungjin.manager;

import java.net.InetAddress;

import me.byungjin.network.Client;
import me.byungjin.network.Server;
import me.byungjin.network.event.DataComeInEvent;

public class NetworkManager {	
	/**
	 * 중앙 서버 생성
	 * @param dataProc
	 * @return
	 * @throws Exception
	 */
	public static Server getServer() throws Exception {
		return new Server(null);
	}
	/**
	 * 사용자 호스트 서버 생성
	 * @param dataProc
	 * @param nick
	 * @return
	 * @throws Exception
	 */
	public static Server getHost(String nick) throws Exception {
		return new Server(nick);
	}
	/**
	 * 중앙 서버 연결용 사용자 생성
	 * @return
	 * @throws Exception
	 */
	public static Client getClientToServer() throws Exception {
		return new Client();
	}
	/**
	 * 사용자 호스트 연결용 사용자 생성
	 * @return
	 * @throws Exception 
	 */
	public static Client getClientToHost(String ip) throws Exception {
		return new Client(ip);
	}	
	/**
	 * Ip 얻기
	 * @return
	 */
	public static String getNetworkIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e) {
			return "Error : can't get Address!";
		}
	}
}
