package me.byungjin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import me.byungjin.game.GameKind;
import me.byungjin.manager.ENVIRONMENT;
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
			conn = DriverManager.getConnection("jdbc:mysql://" + ENVIRONMENT.DB_IP + ":" + ENVIRONMENT.DB_PORT + "/jmini?validationQuery=\"SELECT 1\"", ENVIRONMENT.DB_ID, ENVIRONMENT.DB_PW);
			smt = conn.createStatement();
			isConnect = true;
			SystemManager.message(ENVIRONMENT.DB, "Connection Success!!");
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.DB, e);
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
				SystemManager.message(ENVIRONMENT.DB, "check User(" + id +")");
				return true;
			}
			return false;
		} catch (SQLException e) {
			SystemManager.catchException(ENVIRONMENT.DB, e);			
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
			if(rs.next() && rs.getInt(1) > 0) {
				SystemManager.message(ENVIRONMENT.DB, "login User(" + id + ", " + password + ")");
				return true;
			}			
		} catch (SQLException e) {
			SystemManager.catchException(ENVIRONMENT.DB, e);			
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
				SystemManager.message(ENVIRONMENT.DB, "new User(" + id + ", " + password + ")");
				return true;
			}
		} catch (SQLException e) {
			SystemManager.catchException(ENVIRONMENT.DB, e);			
		}		
		return false;
	}
	/**
	 * �α� �ۼ�, ���⼭�� SystemManager�� ���� ȣ���ϸ� �ȵ�!!
	 * @param tag
	 * @param str
	 * @param warning
	 */
	public void log(ENVIRONMENT tag, String str, boolean warning) {
		String sql = "INSERT INTO Log (Source, Content, Warning) VALUES (?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, tag.toString());
			pstm.setString(2, str);
			pstm.setBoolean(3, warning);
			pstm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * Rank ��������
	 * @return
	 */
	public Rank getRank(String id, GameKind kind) {
		Rank rank;
		try {
			rs = smt.executeQuery("SELECT * From user_rank Where id='" + id + "' AND Kind=" + kind.toValue());
			if(rs.next()) {
				rank = new Rank(id, kind, rs.getInt(3), rs.getInt(4));
			}else {
				rank = new Rank(id, kind);
			}
			return rank;
		} catch (SQLException e) {
			SystemManager.catchException(ENVIRONMENT.DB, e);			
		}
		return null;
	}
	/**
	 * ��ũ ������Ʈ
	 * @param rank 
	 */
	public void updateRank(Rank rank) {
		try {
			if((rank.getVictory() + rank.getLose()) == 1) {
				String sql = "INSERT INTO user_rank (Id, Kind, Victory, Lose) VALUES (?,?,?,?)";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, rank.getId());
				pstm.setInt(2, rank.getGameKind().toValue());				
				pstm.setInt(3, rank.getVictory());
				pstm.setInt(4, rank.getLose());
				pstm.executeUpdate();				
			}else {
				String sql = "UPDATE user_rank set Victory=?, Lose=? WHERE Id=? AND Kind=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, rank.getVictory());
				pstm.setInt(2, rank.getLose());
				pstm.setString(3, rank.getId());
				pstm.setInt(4, rank.getGameKind().toValue());				
				pstm.executeUpdate();
			}
		}
		catch(SQLException e){
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
				logs.add(new LogSchema(rs.getString(1),  rs.getString(2), rs.getBoolean(3), rs.getTime(4)));			
		} catch (SQLException e) {
			SystemManager.catchException(ENVIRONMENT.DB, e);			
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
            SystemManager.message(ENVIRONMENT.DB, "Connection Close!!");            
		}catch(Exception e){			
			SystemManager.catchException(ENVIRONMENT.DB, e);
		}
	}
}
