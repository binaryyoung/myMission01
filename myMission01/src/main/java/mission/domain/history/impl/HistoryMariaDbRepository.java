package mission.domain.history.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mission.domain.history.History;
import mission.domain.history.HistoryRepository;
import mission.domain.wifiinfo.WifiInfo;
import mission.infra.SQLiteManager;

public class HistoryMariaDbRepository implements HistoryRepository{

	private static final HistoryMariaDbRepository instance = new HistoryMariaDbRepository();
	
	public static HistoryMariaDbRepository getInstance() { return instance; }
	
	private HistoryMariaDbRepository() {}
	
	private SQLiteManager sqLiteManager = SQLiteManager.getInstance();
	
	@Override
	public History save(History history) {
		
		Connection conn = sqLiteManager.getConnection();
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO HISTORY (LAT, LNT, REGIST_DT) VALUES (?, ?, NOW())";
		
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, history.getLat());
			ps.setDouble(2, history.getLnt());
			
			int count = ps.executeUpdate();
			
			conn.commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			sqLiteManager.closeConnection();
		}
		
		return history;
	}

	@Override
	public List<History> findAll() {
		List<History> list = new ArrayList<>();
		
		Connection conn = sqLiteManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT "
			+ " ID, LAT, LNT, REGIST_DT "
			+ " FROM HISTORY "
			+ " ORDER BY ID DESC ";
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				History history = new History(
						Long.parseLong(rs.getString(1)),
						Double.parseDouble( rs.getString(2)),
						Double.parseDouble(rs.getString(3)),
						LocalDateTime.parse(rs.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))  
						);
				
				list.add(history);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			sqLiteManager.closeConnection();
			
		}
		
		return list;
		
	}

	@Override
	public Long delete(long id) {
		Connection conn = sqLiteManager.getConnection();
		PreparedStatement ps = null;
		
		String sql = " DELETE FROM HISTORY WHERE ID = ? ";
		Long cnt = -1L;
		try {
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			
			cnt = (long) ps.executeUpdate();
		
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			sqLiteManager.closeConnection();
			
		}
		
		return cnt;
	}
}
