package mission;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import mission.domain.wifiinfo.WifiInfo;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ObjectMapperTest {
	
	private static final String SAMPLE_JSON = "{\"TbPublicWifiInfo\":{\"list_total_count\":17769,\"RESULT\":{\"CODE\":\"INFO-000\",\"MESSAGE\":\"정상 처리되었습니다\"}"
			+ ",\"row\":["
			+ "{\"X_SWIFI_MGR_NO\":\"서울-9091\",\"X_SWIFI_WRDOFC\":\"송파구\",\"X_SWIFI_MAIN_NM\":\"송파제일청소년독서실\",\"X_SWIFI_ADRES1\":\"서울시 송파구가락로19길11\",\"X_SWIFI_ADRES2\":\"송파제일청소년독서실 (옥내3)\",\"X_SWIFI_INSTL_FLOOR\":\"\",\"X_SWIFI_INSTL_TY\":\"6-4. 복지 - 아동청소년\",\"X_SWIFI_INSTL_MBY\":\"디지털뉴딜(LG U+)\",\"X_SWIFI_SVC_SE\":\"과기부WiFi(복지시설)\",\"X_SWIFI_CMCWR\":\"이통사망\",\"X_SWIFI_CNSTC_YEAR\":\"2022\",\"X_SWIFI_INOUT_DOOR\":\"실내\",\"X_SWIFI_REMARS3\":\"\",\"LAT\":\"37.5032\",\"LNT\":\"127.11062\",\"WORK_DTTM\":\"2022-12-13 10:58:19.0\"}"
			+ ",{\"X_SWIFI_MGR_NO\":\"서울-9092\",\"X_SWIFI_WRDOFC\":\"송파구\",\"X_SWIFI_MAIN_NM\":\"송파청소년센터\",\"X_SWIFI_ADRES1\":\"서울시 송파구 중대로 4길4\",\"X_SWIFI_ADRES2\":\"송파청소년센터 (옥내1)\",\"X_SWIFI_INSTL_FLOOR\":\"\",\"X_SWIFI_INSTL_TY\":\"6-4. 복지 - 아동청소년\",\"X_SWIFI_INSTL_MBY\":\"디지털뉴딜(LG U+)\",\"X_SWIFI_SVC_SE\":\"과기부WiFi(복지시설)\",\"X_SWIFI_CMCWR\":\"이통사망\",\"X_SWIFI_CNSTC_YEAR\":\"2022\",\"X_SWIFI_INOUT_DOOR\":\"실내\",\"X_SWIFI_REMARS3\":\"\",\"LAT\":\"37.48976\",\"LNT\":\"127.11147\",\"WORK_DTTM\":\"2022-12-13 10:58:19.0\"}"
			+ ",{\"X_SWIFI_MGR_NO\":\"서울-9093\",\"X_SWIFI_WRDOFC\":\"송파구\",\"X_SWIFI_MAIN_NM\":\"송파청소년센터\",\"X_SWIFI_ADRES1\":\"서울시 송파구 중대로 4길4\",\"X_SWIFI_ADRES2\":\"송파청소년센터 (옥내2)\",\"X_SWIFI_INSTL_FLOOR\":\"\",\"X_SWIFI_INSTL_TY\":\"6-4. 복지 - 아동청소년\",\"X_SWIFI_INSTL_MBY\":\"디지털뉴딜(LG U+)\",\"X_SWIFI_SVC_SE\":\"과기부WiFi(복지시설)\",\"X_SWIFI_CMCWR\":\"이통사망\",\"X_SWIFI_CNSTC_YEAR\":\"2022\",\"X_SWIFI_INOUT_DOOR\":\"실내\",\"X_SWIFI_REMARS3\":\"\",\"LAT\":\"37.48976\",\"LNT\":\"127.11147\",\"WORK_DTTM\":\"2022-12-13 10:58:19.0\"}"
			+ "]}}";

	@Test
	void jsonNodeToStringTest() throws JsonMappingException, JsonProcessingException {
		// given
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode wifiInfoNode = objectMapper.readTree(SAMPLE_JSON).get("TbPublicWifiInfo");
		
		// when
		String code = wifiInfoNode.get("RESULT").get("CODE").textValue();
		String mgrNo = wifiInfoNode.get("row").get(0).get("X_SWIFI_MGR_NO").textValue();
		
		// then
		assertThat(code).isEqualTo("INFO-000");
		assertThat(mgrNo).isEqualTo("서울-9091");
	}

	
	@Test
	void jsonNodeToValueObjectTest() throws JsonMappingException, JsonProcessingException {
		// given		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode wifiInfoNode = objectMapper.readTree(SAMPLE_JSON).get("TbPublicWifiInfo");
		
		
		// when
		WifiInfo wi = objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_SNAKE_CASE)
								.readerFor(WifiInfo.class)
								.readValue(wifiInfoNode.get("row").get(0).toString());
		
		// then 
		assertThat(wi).isNotNull();
		assertThat(wi.getxSwifiMgrNo()).isEqualTo("서울-9091");
	}
	
	@Test
	void jsonNodeToListTest() throws JsonMappingException, JsonProcessingException {
		// given
		List<WifiInfo> list = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		
		// when
		Iterator<JsonNode> iterator = objectMapper.readTree(SAMPLE_JSON).get("TbPublicWifiInfo").get("row").elements();
		iterator.forEachRemaining(j -> {
			try {
				list.add( objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_SNAKE_CASE)
						.readerFor(WifiInfo.class)
						.readValue(j.toString())
						);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
		
		
		// then
		assertThat(list.size()).isSameAs(3);
	}
	
}
