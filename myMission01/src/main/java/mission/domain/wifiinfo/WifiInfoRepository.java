package mission.domain.wifiinfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import mission.util.DistanceCalculator;
import mission.util.DistanceCalculator.DistanceUnit;

public class WifiInfoRepository {

	private static List<WifiInfo> store = new ArrayList<>();
	
	/*
	 * DB 없이 구현하기
	 * 1. 싱글턴 처리 
	 */
	private static final WifiInfoRepository instance = new WifiInfoRepository();
	
	public static WifiInfoRepository getInstance() { return instance; }
	
	private WifiInfoRepository() {}
	
	public WifiInfo save(WifiInfo wifiInfo) {
		store.add(wifiInfo);
		return wifiInfo;
	}
	
	public List<WifiInfo> findByLatAndLnt(double lat, double lnt) {
		
		// 거리 재설정 (소수점 4자리 자르기)
		for (WifiInfo wifiInfo : store) {
			double distance = DistanceCalculator.getDistance(lat, lnt, wifiInfo.getLat(), wifiInfo.getLnt(), DistanceUnit.KILOMETER);
			double scale = Math.pow(10, 4);
			distance = Math.round(distance * scale)/scale;
			wifiInfo.setDistance(distance);
		}

		// TODO stream 정리
		// 거리순서로 정렬 후 20개 반환
		return store.stream().sorted(Comparator.comparing(WifiInfo::getDistance))
				.limit(20).collect(Collectors.toList());
	}
	
	public void clearStore() {
		store.clear();
	}
}
