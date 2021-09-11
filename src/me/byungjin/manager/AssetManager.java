package me.byungjin.manager;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.swing.ImageIcon;

public class AssetManager {	
	private static File env;
	
	/**
	 * �ý��� �⺻ ���� ��Ʈ
	 */
	static public Font DEFAULT_FONT;
	
	static public ImageIcon ICON_MONITER;
	static public ImageIcon ICON_MONITER_SELECTED;
	static public ImageIcon ICON_LOG;
	static public ImageIcon ICON_LOG_SELECTED;
	static public ImageIcon ICON_SETTING;
	static public ImageIcon ICON_SETTING_SELECTED;	
	static public ImageIcon ICON_SWITCH_ON;
	static public ImageIcon ICON_SWITCH_OFF;
	
	static public void init() {
		readFont();
		readIcons();		
	}
	/**
	 * ��Ʈ ������ �о��
	 */
	static public void readFont() {
		InputStream is = AssetManager.class.getResourceAsStream("../../../assets/fonts/NanumSquareRoundB.ttf");
		try {
			DEFAULT_FONT = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(15f).deriveFont(Font.BOLD);					
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	/**
	 * ������ ������ �о��
	 */
	static public void readIcons() {
		ICON_MONITER = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_monitor.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_MONITER_SELECTED = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_monitor_full.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_LOG = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_chat.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_LOG_SELECTED = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_chat_full.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_SETTING = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_settings.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_SETTING_SELECTED = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_settings_full.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_SWITCH_ON= new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_switch_on.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		ICON_SWITCH_OFF= new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/icons/icon_switch_off.png")).getScaledInstance(25, 25, Image.SCALE_SMOOTH));
	}	
	/**
	 * IP �� ȯ�� ���� ������ ����(������ ���� ��� �⺻ ���� ����)
	 * @param 
	 * @return
	 */
	static public boolean getEnv(boolean admin) {
		try {
			env = new File("env.txt");						
			if(env.exists()) {
				readEnv(admin);
				return true;
			}else {
				env.createNewFile();
				writeDefaultEnv(admin);
				return false;
			}
		}catch(Exception e) {
			SystemManager.catchException(Environment.ASSETSMANAGER, e);
			return false;
		}		
	}
	/**
	 * �⺻ ȯ�� ���� ������ ����
	 * @param admin true�� ��� Admin�� ȯ�� ���� ���� ����
	 * @throws Exception
	 */
	static private void writeDefaultEnv(boolean admin) throws Exception{
		saveEnv(admin);
	}	
	/**
	 * ȯ�� ���� ������ ����  
	 * @param admin true�� ��� Admin ������ ����
	 * @throws Exception
	 */
	static private void readEnv(boolean admin) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(env));
		Environment.SERVER_IP = reader.readLine();		
		Environment.SERVER_IP = Environment.SERVER_IP.substring(Environment.SERVER_IP.indexOf("=") + 1);
		
		if(!admin) {
			reader.close();
			return;
		}
		
		Environment.DB_IP = reader.readLine();
		Environment.DB_IP = Environment.DB_IP.substring(Environment.DB_IP.indexOf("=") + 1);
		Environment.DB_PORT = reader.readLine();
		Environment.DB_PORT = Environment.DB_PORT.substring(Environment.DB_PORT.indexOf("=") + 1);
		Environment.DB_ID = reader.readLine();
		Environment.DB_ID = Environment.DB_ID.substring(Environment.DB_ID.indexOf("=") + 1);
		Environment.DB_PW = reader.readLine();		
		Environment.DB_PW = Environment.DB_PW.substring(Environment.DB_PW.indexOf("=") + 1);
		reader.close();
	}	
	/**
	 * ȯ�� ���� ���� ����
	 */
	static public void saveEnv(boolean admin) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(env));
			writer.println("SERVER_IP=" + Environment.SERVER_IP);
			if(!admin) {				
				writer.close();
				return;
			}
			writer.println("DB_IP=" + Environment.DB_IP);
			writer.println("DB_PORT=" + Environment.DB_PORT);
			writer.println("DB_ID=" + Environment.DB_ID);
			writer.println("DB_PW=" + Environment.DB_PW);
			writer.close();
		}catch(Exception e) {
			SystemManager.catchException(Environment.ASSETSMANAGER, e);
		}
	}
}
