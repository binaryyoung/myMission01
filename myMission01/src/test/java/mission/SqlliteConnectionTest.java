package mission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import mission.infra.SQLiteManager;

public class SqlliteConnectionTest {

	
	
	
	
	
	@Test
	void test() throws SQLException {
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
	}
}
