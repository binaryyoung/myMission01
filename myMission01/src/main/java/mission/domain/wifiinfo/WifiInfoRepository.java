package mission.domain.wifiinfo;

import java.util.List;

public interface WifiInfoRepository {

	public int saveAll(List<WifiInfo> wifiInfoList);
	
	public List<WifiInfo> findByLatAndLnt(double lat, double lnt);	
	
	public void clearStore();
}
