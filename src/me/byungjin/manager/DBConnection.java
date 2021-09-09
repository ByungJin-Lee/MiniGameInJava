package me.byungjin.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private Connection conn;
	private Statement smt;
	private ResultSet rs;
	private PreparedStatement pstm;
	
	public DBConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + Environment.DB_IP + ":" + Environment.DB_PORT + "/jmini", Environment.DB_ID, Environment.DB_PW);
		smt = conn.createStatement();
		SystemManager.message(Environment.DB, "DB Connection Success!!");
	}
	
	public boolean isUser(String id) {
		String sql = "SELECT COUNT(*) as cnt From User Where id='" + id + "'";
		try {
			rs = smt.executeQuery(sql);			
			if(rs.next() && rs.getInt(1) > 0) {
				SystemManager.message(Environment.DB, "check User(" + id +")");
				return true;
			}else {
				return false;
			}			
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}		
		return false;		
	}
	public boolean confirmUser(String id, String password) {
		String sql = "SELECT COUNT(*) as cnt From User Where id='" + id + "' AND Pw='" + password + "'";
		try {
			rs = smt.executeQuery(sql);
			if(rs.getInt(1) > 0) {
				SystemManager.message(Environment.DB, "login User(" + id + ", " + password + ")");
				return true;
			}else {
				return false;
			}			
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}		
		return false;
	}	
	public boolean registerUser(String id, String password) {
		String sql = "INSERT INTO User VALUES (?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, password);
			if(pstm.executeUpdate() > 0) {
				SystemManager.message(Environment.DB, "new User(" + id + ", " + password + ")");
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			SystemManager.catchException(Environment.DB, e);			
		}		
		return false;
	}
	public void close() {
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
