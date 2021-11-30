package me.byungjin.manager;

import java.net.InetAddress;

import me.byungjin.network.Client;
import me.byungjin.network.Server;
import me.byungjin.network.event.DataComeInEvent;

public class NetworkManager {	
	/**
	 * �߾� ���� ����
	 * @param dataProc
	 * @return
	 * @throws Exception
	 */
	public static Server getServer() throws Exception {
		return new Server(null);
	}
	/**
	 * ����� ȣ��Ʈ ���� ����
	 * @param dataProc
	 * @param nick
	 * @return
	 * @throws Exception
	 */
	public static Server getHost(String nick) throws Exception {
		return new Server(nick);
	}
	/**
	 * �߾� ���� ����� ����� ����
	 * @return
	 * @throws Exception
	 */
	public static Client getClientToServer() throws Exception {
		return new Client();
	}
	/**
	 * ����� ȣ��Ʈ ����� ����� ����
	 * @return
	 * @throws Exception 
	 */
	public static Client getClientToHost(String ip) throws Exception {
		return new Client(ip);
	}	
	/**
	 * Ip ���
	 * @return
	 */
	public static String getNetworkIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e) {
			return "000";
		}
	}
}
