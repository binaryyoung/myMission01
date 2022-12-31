package mission.domain.wifiinfo.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import mission.domain.wifiinfo.WifiInfo;
import mission.domain.wifiinfo.WifiInfoRepository;
import mission.infra.SQLiteManager;
import mission.util.DistanceCalculator;
import mission.util.DistanceCalculator.DistanceUnit;

public class WifiInfoMariaDbRepository implements WifiInfoRepository{

	private static final WifiInfoMariaDbRepository instance = new WifiInfoMariaDbRepository();
	
	public static WifiInfoMariaDbRepository getInstance() { return instance; }
	
	private WifiInfoMariaDbRepository() {}
	
	private SQLiteManager sqLiteManager = SQLiteManager.getInstance();
	
	@Override
	public int saveAll(List<WifiInfo> wifiInfos) {
		int totalCnt = 0;
		
		Connection conn = sqLiteManager.getConnection();
		PreparedStatement ps = null;
		
		String sql = " INSERT INTO WIFI_INFO ( "
				+ " X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, "
				+ " X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, "
				+ " X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
				+ " X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM ) "
				+ " VALUES ( "
				+ " ? , ? , ? , ? , "
				+ " ? , ? , ? , ? , "
				+ " ? , ? , ? , ? , "
				+ " ? , ? , ? , ? ) ";
		try {
			
			ps = conn.prepareStatement(sql);
			
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
				ps.setString(11, wifiInfo.getxSwifiCnstcYear().equals("") ? null : wifiInfo.getxSwifiCnstcYear());
				ps.setString(12, wifiInfo.getxSwifiInoutDoor());
				ps.setString(13, wifiInfo.getxSwifiRemars3());
				ps.setDouble(14, wifiInfo.getLat());
				ps.setDouble(15, wifiInfo.getLnt());
				ps.setString(16, wifiInfo.getWorkDttm());
				
				ps.addBatch();

			}
			
			totalCnt = ps.executeBatch().length;
			
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
		
		return totalCnt;
	}

	@Override
	public List<WifiInfo> findByLatAndLnt(double lat, double lnt) {
		
		List<WifiInfo> list = findAll();
		
		// 거리 재설정 (소수점 4자리 자르기)
		for (WifiInfo wifiInfo : list) {
			double distance = DistanceCalculator.getDistance(lat, lnt, wifiInfo.getLat(), wifiInfo.getLnt(), DistanceUnit.KILOMETER);
			double scale = Math.pow(10, 4);
			distance = Math.round(distance * scale)/scale;
			wifiInfo.setDistance(distance);
		}

		// TODO stream 정리
		// 거리순서로 정렬 후 20개 반환
		return list.stream().sorted(Comparator.comparing(WifiInfo::getDistance))
				.limit(20).collect(Collectors.toList());
	}

	@Override
	public void clearStore() {
		
		Connection conn = sqLiteManager.getConnection();
		PreparedStatement ps = null;
		
		String sql = " DELETE FROM WIFI_INFO ";
		
		try {
			
			ps = conn.prepareStatement(sql);
			
			int cnt = ps.executeUpdate();
			
			ps.executeQuery();
			
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
	}
	
	public List<WifiInfo> findAll() {
		List<WifiInfo> list = new ArrayList<>();
		
		Connection conn = sqLiteManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT "
			+ " X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, "
			+ " X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, "
			+ " X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, "
			+ " X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM "
			+ " FROM WIFI_INFO ";
		
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				WifiInfo wifiInfo = new WifiInfo(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getDouble(14),
						rs.getDouble(15),
						rs.getString(16),
						null
						);
				
				list.add(wifiInfo);
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

}
