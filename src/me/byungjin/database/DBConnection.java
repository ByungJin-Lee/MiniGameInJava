package me.byungjin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import me.byungjin.manager.Environment;
import me.byungjin.manager.SystemManager;

public class DBConnection {
	public boolean isConnect = false;
	private Connection conn;
	private Statement smt;
	private ResultSet rs;
	private PreparedStatement pstm;
	
	/**
	 * DB�� ����
	 * @throws Exception
	 */
	public DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");		
			conn = DriverManager.getConnection("jdbc:mysql://" + Environment.DB_IP + ":" + Environment.DB_PORT + "/jmini", Environment.DB_ID, Environment.DB_PW);
			smt = conn.createStatement();
			isConnect = true;
			SystemManager.message(Environment.DB, "DB Connection Success!!");
		}catch(Exception e) {
			SystemManager.catchException(Environment.DB, e);
		}
	}
	/**
	 * id�� ���� ������ �ִ��� Ȯ����
	 * @param id
	 * @return
	 */
	public boolean isUser(String id) {
		String sql = "SELECT COUNT(*) as cnt From User Where id='" + id + "'";
		try {
			rs = smt.executeQuery(sql);			
			if(rs.next() && rs.getInt(1) > 0) {
				SystemManager.message(Environment.DB, "check User(" + id +")");
				return true;
			}		
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}		
		return false;		
	}
	/**
	 * id�� password�� ���� ������ �ִ��� Ȯ���� login
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean confirmUser(String id, String password) {
		String sql = "SELECT COUNT(*) as cnt From User Where id='" + id + "' AND Pw='" + password + "'";
		try {
			rs = smt.executeQuery(sql);
			if(rs.getInt(1) > 0) {
				SystemManager.message(Environment.DB, "login User(" + id + ", " + password + ")");
				return true;
			}			
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}		
		return false;
	}	
	/**
	 * id�� password�� ���� ������ DB�� ��Ͻ�Ŵ
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean registerUser(String id, String password) {
		String sql = "INSERT INTO User VALUES (?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, password);
			if(pstm.executeUpdate() > 0) {
				SystemManager.message(Environment.DB, "new User(" + id + ", " + password + ")");
				return true;
			}
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}		
		return false;
	}
	/**
	 * �α� �ۼ�, ���⼭�� SystemManager�� ���� ȣ���ϸ� �ȵ�!!
	 * @param tag
	 * @param str
	 * @param warning
	 */
	public void log(short tag, String str, boolean warning) {
		String sql = "INSERT INTO Log (Source, Content, Warning) VALUES (?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setShort(1, tag);
			pstm.setString(2, str);
			pstm.setBoolean(3, warning);
			pstm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * DB�� ����� �α׸� ������
	 * @return
	 */
	public ArrayList<LogSchema> getLog() {
		ArrayList<LogSchema> logs = new ArrayList<LogSchema>();
		try {
			rs = smt.executeQuery("SELECT * From Log");			
			while(rs.next())
				logs.add(new LogSchema(rs.getShort(1),  rs.getString(2), rs.getBoolean(3), rs.getTime(4)));			
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}
		return logs;		
	}
	/**
	 * DB�� ������ ������
	 */
	public void close() {
		isConnect = false;
		try {
			if(conn != null && !conn.isClosed())
                conn.close();            
            if(smt != null && !smt.isClosed())
            	smt.close();            
            if(rs != null && !rs.isClosed())
                rs.close();            
            SystemManager.message(Environment.DB, "DB Connection Close!!");            
		}catch(Exception e){			
			SystemManager.catchException(Environment.DB, e);
		}
	}
}
