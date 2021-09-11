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
	 * 시스템 기본 적용 폰트
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
	 * 폰트 파일을 읽어옴
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
	 * 아이콘 파일을 읽어옴
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
	 * IP 등 환경 설정 파일을 읽을(파일이 없는 경우 기본 파일 생성)
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
	 * 기본 환경 설정 파일을 만듬
	 * @param admin true인 경우 Admin용 환경 설정 파일 생성
	 * @throws Exception
	 */
	static private void writeDefaultEnv(boolean admin) throws Exception{
		saveEnv(admin);
	}	
	/**
	 * 환경 설정 파일을 읽음  
	 * @param admin true인 경우 Admin 값까지 읽음
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
	 * 환경 설정 파일 저장
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
