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
	static public ImageIcon ICON_WHITE_STONE;
	static public ImageIcon ICON_BLACK_STONE;
	static public ImageIcon ICON_BLACK_STONE_OP;
	static public ImageIcon ICON_WHITE_STONE_OP;
	
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
	static public void readOmokIcons() {
		ICON_WHITE_STONE = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/game/white.png")).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		ICON_BLACK_STONE = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/game/black.png")).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		ICON_BLACK_STONE_OP = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/game/black_op.png")).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		ICON_WHITE_STONE_OP = new ImageIcon(Toolkit.getDefaultToolkit().createImage(AssetManager.class.getResource("../../../assets/game/white_op.png")).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
	}
	static public Image readBoard() {
		return new ImageIcon(AssetManager.class.getResource("../../../assets/game/baduk_board.png")).getImage();
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
			SystemManager.catchException(ENVIRONMENT.ASSETSMANAGER, e);
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
		ENVIRONMENT.SERVER_IP = reader.readLine();		
		ENVIRONMENT.SERVER_IP = ENVIRONMENT.SERVER_IP.substring(ENVIRONMENT.SERVER_IP.indexOf("=") + 1);
		
		if(!admin) {
			reader.close();
			return;
		}
		
		ENVIRONMENT.DB_IP = reader.readLine();
		ENVIRONMENT.DB_IP = ENVIRONMENT.DB_IP.substring(ENVIRONMENT.DB_IP.indexOf("=") + 1);
		ENVIRONMENT.DB_PORT = reader.readLine();
		ENVIRONMENT.DB_PORT = ENVIRONMENT.DB_PORT.substring(ENVIRONMENT.DB_PORT.indexOf("=") + 1);
		ENVIRONMENT.DB_ID = reader.readLine();
		ENVIRONMENT.DB_ID = ENVIRONMENT.DB_ID.substring(ENVIRONMENT.DB_ID.indexOf("=") + 1);
		ENVIRONMENT.DB_PW = reader.readLine();		
		ENVIRONMENT.DB_PW = ENVIRONMENT.DB_PW.substring(ENVIRONMENT.DB_PW.indexOf("=") + 1);
		reader.close();
	}	
	/**
	 * 환경 설정 파일 저장
	 */
	static public void saveEnv(boolean admin) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(env));
			writer.println("SERVER_IP=" + ENVIRONMENT.SERVER_IP);
			if(!admin) {				
				writer.close();
				return;
			}
			writer.println("DB_IP=" + ENVIRONMENT.DB_IP);
			writer.println("DB_PORT=" + ENVIRONMENT.DB_PORT);
			writer.println("DB_ID=" + ENVIRONMENT.DB_ID);
			writer.println("DB_PW=" + ENVIRONMENT.DB_PW);
			writer.close();
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.ASSETSMANAGER, e);
		}
	}
}
