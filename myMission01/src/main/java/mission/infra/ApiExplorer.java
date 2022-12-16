package mission.infra;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
	
	/**
	 * 서울시 공공와이파이 정보를 가져온다.
	 * 
	 * @param beginIndex
	 * @param endIndex
	 * @return json string
	 * @throws IOException
	 */
	public String requestApi(int beginIndex, int endIndex) throws IOException {
		URL url = createUrlStringByRange(1, 1000);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		/* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
		// System.out.println("Response code: " + conn.getResponseCode()); 
		
		BufferedReader br;
		
		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuffer response = new StringBuffer();
		String inputLine = br.readLine();
		
		while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
		
		br.close();
		conn.disconnect();
		return response.toString();
	}
	
	private URL createUrlStringByRange(int beginIndex, int endIndex) throws UnsupportedEncodingException, MalformedURLException {
		// TODO properties 기반 조회로 변경.
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" + URLEncoder.encode("577761646e74776f333956746b7a42","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode(String.valueOf(beginIndex),"UTF-8")); /* 요청시작위치 */
		urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endIndex),"UTF-8")); /* 요청종료위치 */
		return new URL(urlBuilder.toString());
	}
}
