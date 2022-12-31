package mission.domain.history;

import java.time.LocalDateTime;

// TODO History 메서드 및 저장소 생성
public class History {

	private long id;
	private double lat;
	private double lnt;
	private LocalDateTime registDt;
	
	public History() {
	}
	
	public History(double lat, double lnt, LocalDateTime registDt) {
		this.lat = lat;
		this.lnt = lnt;
		this.registDt = registDt;
	}

	public History(long id, double lat, double lnt, LocalDateTime registDt) {
		this.id = id;
		this.lat = lat;
		this.lnt = lnt;
		this.registDt = registDt;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setX(double lat) {
		this.lat = lat;
	}
	public double getLnt() {
		return lnt;
	}
	public void setLnt(double y) {
		this.lnt = lnt;
	}
	public LocalDateTime getRegistDt() {
		return registDt;
	}
	public void setRegistDt(LocalDateTime registDt) {
		this.registDt = registDt;
	}
	
	
}
