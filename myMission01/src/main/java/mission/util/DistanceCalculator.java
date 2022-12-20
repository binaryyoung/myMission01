package mission.util;

public class DistanceCalculator {
	
	public enum DistanceUnit {
		KILOMETER, METER
	}
	
	/**
	 * 두 지점간의 거리 계산
	 * 
	 * @param lat1 지점 1 위도
	 * @param lon1 지점 1 경도 
	 * @param lat2 지점 2 위도
	 * @param lon2 지점 2 경도
	 * @param unit 거리 표출단위 
	 * @return
	 * 
	 * - 클래스 내부의 static 변수나 외부파라미터에만 의존한다면 정적메서드를 사용하는 것이 낫다. 
	 */
	public static double getDistance(double lat1, double lon1, double lat2, double lon2, DistanceUnit distanceUnit) {
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		if (distanceUnit == DistanceUnit.KILOMETER) {
			dist = dist * 1.609344;
		} else if(distanceUnit == DistanceUnit.METER){
			dist = dist * 1609.344;
		} 

		return (dist);
	}
	
	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
