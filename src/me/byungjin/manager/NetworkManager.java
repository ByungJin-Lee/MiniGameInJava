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
	public static Server getServer(DataComeInEvent dataProc) throws Exception {
		return new Server(dataProc, null);
	}
	/**
	 * ����� ȣ��Ʈ ���� ����
	 * @param dataProc
	 * @param nick
	 * @return
	 * @throws Exception
	 */
	public static Server getHost(DataComeInEvent dataProc, String nick) throws Exception {
		return new Server(dataProc, nick);
	}
	/**
	 * �߾� ���� ����� ����� ����
	 * @return
	 * @throws Exception
	 */
	public static Client getClientToServer(DataComeInEvent dataProc) throws Exception {
		return new Client(dataProc);
	}
	/**
	 * ����� ȣ��Ʈ ����� ����� ����
	 * @return
	 * @throws Exception 
	 */
	public static Client getClientToHost(DataComeInEvent dataProc, String ip) throws Exception {
		return new Client(dataProc, ip);
	}	
	/**
	 * Ip ���
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
