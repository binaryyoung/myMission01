package mission;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import mission.domain.wifiinfo.WifiInfo;
import mission.infra.SQLiteManager;

public class SqlliteConnectionTest {

	@Test
	void test() throws SQLException {
		/*
		SQLiteManager manager = new SQLiteManager();
        Connection conn = manager.createConnection();     // 연결
        PreparedStatement ps;
        
        ps = conn.prepareStatement(" create table history ( "
        		+ " id int primary key, "
        		+ " x decimal(10,10), "
        		+ " y varchar(10,10), "
        		+ " registDate timestamp ) ");
        
        int count = ps.executeUpdate();
        ps.close();
        
        System.out.println("create 완료 count : " + count);
        
        for (int i = 1; i <= 10; i++) {
        	ps = conn.prepareStatement(" insert into history(id, x, y, registDate) "
        			+ " values( " 
        			+ " " + i + ", "
        			+ " " + 11.1 + ", "
        			+ " " + 11.1 + ", "
        			+ " datetime('now','localtime') "
        			+  " ) ");
        	
        	count = ps.executeUpdate();
        	System.out.println("insert(" + i + ") cnt : " + count);
        	ps.close();
        	
        }
        
        ps = conn.prepareStatement(" select id, x, y, registDate from history ");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
        	System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4));
        }
        
        rs.close();
        ps.close();
        manager.closeConnection();      // 연결 해제
        */
	}
	
	@Test
	void test2() throws SQLException {
		SQLiteManager manager = SQLiteManager.getInstance();
        Connection conn = manager.getConnection();     // 연결
        PreparedStatement ps = null;
        
		String sql = 
		" CREATE TABLE WIFI_INFO " +
		" ( " +
		"      X_SWIFI_MGR_NO       VARCHAR2(50)     NOT NULL    , " + 
		"  X_SWIFI_WRDOFC       VARCHAR2(50)     NULL        ,  " +
		"  X_SWIFI_MAIN_NM      VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_ADRES1       VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_ADRES2       VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_INSTL_FLOOR  VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_INSTL_TY     VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_INSTL_MBY    VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_SVC_SE       VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_CMCWR        VARCHAR2(100)    NULL        ,  " +
		"  X_SWIFI_CNSTC_YEAR   INT              NULL        ,  " +
		"  X_SWIFI_INOUT_DOOR   VARCHAR2(30)     NULL        ,  " +
		"  X_SWIFI_REMARS3      VARCHAR2(300)    NULL        ,  " +
		"  LAT                  DECIMAL(3, 10)    NULL        ,  " +
		"  LNT                  DECIMAL(3, 10)    NULL        ,  " +
		"  WORK_DTTM            DATETIME         NULL        ,  " +
		"  PRIMARY KEY (X_SWIFI_MGR_NO)  " +
		"  )  ";
        
		ps = conn.prepareStatement(sql);
		ps.executeUpdate();		
		
	    int totalCnt = 0;
	    
	    DatabaseMetaData meta = conn.getMetaData();
	    
        // 테이블 목록 조회
        ResultSet rs = meta.getTables(null, null, "WIFI_INFO", null);
        while(rs.next()) {
            String table = rs.getString("TABLE_NAME");
            System.out.println("Table Name : " + table);
        }
        
	    List<WifiInfo> wifiInfos = new ArrayList<>();
	    for (int i = 1; i <= 10; i++) {
	    	wifiInfos.add(new WifiInfo(String.valueOf(i)));
	    }
		
		sql = " INSERT INTO WIFI_INFO ( "
			+ " X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, "
			+ " X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, "
			+ " X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
			+ " X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM ) "
			+ " VALUES ( "
			+ " ?, ?, ?, ?, "
			+ " ?, ?, ?, ?, "
			+ " ?, ?, ?, ?, "
			+ " ?, ?, ?, ? ) ";
		
		try {
			ps = conn.prepareStatement(sql);
			
			int cnt = 0;
			
			for (WifiInfo wifiInfo : wifiInfos) {
				ps.setString(1, wifiInfo.getxSwifiMgrNo());
				ps.setString(2, wifiInfo.getxSwifiWrdofc());
				ps.setString(3, wifiInfo.getxSwifiMainNm());
				ps.setString(4, wifiInfo.getxSwifiAdres1());
				ps.setString(5, wifiInfo.getxSwifiAdres2());
				ps.setString(6, wifiInfo.getxSwifiInstlFloor());
				ps.setString(7, wifiInfo.getxSwifiInstlTy());
				ps.setString(8, wifiInfo.getxSwifiInstlMby());
				ps.setString(9, wifiInfo.getxSwifiSvcSe());
				ps.setString(10, wifiInfo.getxSwifiCmcwr());
				ps.setString(11, wifiInfo.getxSwifiCnstcYear());
				ps.setString(12, wifiInfo.getxSwifiInoutDoor());
				ps.setString(13, wifiInfo.getxSwifiRemars3());
				ps.setDouble(14, 1.0);
				ps.setDouble(15, 1.0);
				ps.setString(16, wifiInfo.getWorkDttm());
				
				ps.addBatch();
				cnt++;
			}
			
			totalCnt += ps.executeBatch().length;
			
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
			
			manager.closeConnection();
			
		}
		

	}
}
