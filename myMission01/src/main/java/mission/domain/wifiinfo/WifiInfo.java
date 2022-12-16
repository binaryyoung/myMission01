package mission.domain.wifiinfo;

public class WifiInfo {
	/*
	"X_SWIFI_MGR_NO":"서울-9091",
	"X_SWIFI_WRDOFC":"송파구",
	"X_SWIFI_MAIN_NM":"송파제일청소년독서실",
	"X_SWIFI_ADRES1":"서울시 송파구가락로19길11",
	"X_SWIFI_ADRES2":"송파제일청소년독서실 (옥내3)",
	"X_SWIFI_INSTL_FLOOR":"",
	"X_SWIFI_INSTL_TY":"6-4. 복지 - 아동청소년",
	"X_SWIFI_INSTL_MBY":"디지털뉴딜(LG U+)",
	"X_SWIFI_SVC_SE":"과기부WiFi(복지시설)",
	"X_SWIFI_CMCWR":"이통사망",
	"X_SWIFI_CNSTC_YEAR":"2022",
	"X_SWIFI_INOUT_DOOR":"실내",
	"X_SWIFI_REMARS3":"",
	"LAT":"37.5032",
	"LNT":"127.11062",
	"WORK_DTTM":"2022-12-13 10:58:19.0"
	*/
	
	private String xSwifiMgrNo; // X_SWIFI_MGR_NO
	private String xSwifiWrdofc;  // X_SWIFI_WRDOFC
	private String xSwifiMainNm; // X_SWIFI_MAIN_NM
	private String xSwifiAdres1; // X_SWIFI_ADRES1
	private String xSwifiAdres2; // X_SWIFI_ADRES2
	private String xSwifiInstlFloor; // X_SWIFI_INSTL_FLOOR
	private String xSwifiInstlTy; // X_SWIFI_INSTL_TY
	private String xSwifiInstlMby; // X_SWIFI_INSTL_MBY
	private String xSwifiSvcSe; // X_SWIFI_SVC_SE
	private String xSwifiCmcwr; // X_SWIFI_CMCWR
	private String xSwifiCnstcYear; // X_SWIFI_CNSTC_YEAR
	private String xSwifiInoutDoor; // X_SWIFI_INOUT_DOOR
	private String xSwifiRemars3; // X_SWIFI_REMARS3
	private Double lat; // LAT
	private Double lnt; // LNT
	private String workDttm; // WORK_DTTM
	
	private Double distance;
	
	public WifiInfo() {}

	public WifiInfo(String xSwifiMgrNo, String xSwifiWrdofc, String xSwifiMainNm, String xSwifiAdres1,
			String xSwifiAdres2, String xSwifiInstlFloor, String xSwifiInstlTy, String xSwifiInstlMby,
			String xSwifiSvcSe, String xSwifiCmcwr, String xSwifiCnstcYear, String xSwifiInoutDoor,
			String xSwifiRemars3, Double lat, Double lnt, String workDttm, Double distance) {
		super();
		this.xSwifiMgrNo = xSwifiMgrNo;
		this.xSwifiWrdofc = xSwifiWrdofc;
		this.xSwifiMainNm = xSwifiMainNm;
		this.xSwifiAdres1 = xSwifiAdres1;
		this.xSwifiAdres2 = xSwifiAdres2;
		this.xSwifiInstlFloor = xSwifiInstlFloor;
		this.xSwifiInstlTy = xSwifiInstlTy;
		this.xSwifiInstlMby = xSwifiInstlMby;
		this.xSwifiSvcSe = xSwifiSvcSe;
		this.xSwifiCmcwr = xSwifiCmcwr;
		this.xSwifiCnstcYear = xSwifiCnstcYear;
		this.xSwifiInoutDoor = xSwifiInoutDoor;
		this.xSwifiRemars3 = xSwifiRemars3;
		this.lat = lat;
		this.lnt = lnt;
		this.workDttm = workDttm;
		this.distance = distance;
	}

	public String getxSwifiMgrNo() {
		return xSwifiMgrNo;
	}

	public String getxSwifiWrdofc() {
		return xSwifiWrdofc;
	}

	public String getxSwifiMainNm() {
		return xSwifiMainNm;
	}

	public String getxSwifiAdres1() {
		return xSwifiAdres1;
	}

	public String getxSwifiAdres2() {
		return xSwifiAdres2;
	}

	public String getxSwifiInstlFloor() {
		return xSwifiInstlFloor;
	}

	public String getxSwifiInstlTy() {
		return xSwifiInstlTy;
	}

	public String getxSwifiInstlMby() {
		return xSwifiInstlMby;
	}

	public String getxSwifiSvcSe() {
		return xSwifiSvcSe;
	}

	public String getxSwifiCmcwr() {
		return xSwifiCmcwr;
	}

	public String getxSwifiCnstcYear() {
		return xSwifiCnstcYear;
	}

	public String getxSwifiInoutDoor() {
		return xSwifiInoutDoor;
	}

	public String getxSwifiRemars3() {
		return xSwifiRemars3;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLnt() {
		return lnt;
	}

	public String getWorkDttm() {
		return workDttm;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
	
	

	
}
