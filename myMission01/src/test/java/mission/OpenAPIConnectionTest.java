package mission;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OpenAPIConnectionTest {

	@Test
	void test() throws IOException{
		// given
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" + URLEncoder.encode("577761646e74776f333956746b7a42","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode(String.valueOf(1),"UTF-8")); /* 요청시작위치 */
		urlBuilder.append("/" + URLEncoder.encode(String.valueOf(1),"UTF-8")); /* 요청종료위치 */
		
		// when
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		
		// then
		/*
		 * isSameAs : 메모리상 같은 객체를 가리키는지 비교 (주소 비교)
		 * isEqualTo : 객체가 같은 값을 가지고 있는지 비교한다.
		 */
		assertThat(responseCode).isEqualTo(HttpURLConnection.HTTP_OK);
		assertThat(responseMessage).isEqualTo("OK");
		
	}

}
