package mission.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import mission.domain.wifiinfo.WifiInfo;
import mission.domain.wifiinfo.WifiInfoRepository;
import mission.domain.wifiinfo.impl.WifiInfoJavaBeanRepository;
import mission.domain.wifiinfo.impl.WifiInfoMariaDbRepository;
import mission.infra.ApiExplorer;


@WebServlet("/init")
public class WifiInfoAddServlet extends HttpServlet{
	
	private WifiInfoRepository wifiInfoRepository = WifiInfoMariaDbRepository.getInstance();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// System.out.println("init start");
		
		// 저장소 비우기
		wifiInfoRepository.clearStore();
		
		// 저장
		int beginIndex = 1;
		int endIndex = 1000;
		
		ApiExplorer apiExplorer = new ApiExplorer();
		
		// 테스트 요청 후 전체 건수 조회
		ObjectMapper objectMapper = new ObjectMapper();
		String json = apiExplorer.requestApi(1, 1);
		int listTotalCount = objectMapper.readTree(json).get("TbPublicWifiInfo").get("list_total_count").asInt();
		
		List<WifiInfo> list = new ArrayList<>();
		
		int cnt = 0;
		
		while (beginIndex < listTotalCount) {
			json = apiExplorer.requestApi(beginIndex, endIndex);
			
			String code = objectMapper.readTree(json).get("TbPublicWifiInfo").get("RESULT").get("CODE").asText();
			if (!"INFO-000".equals(code)) {
				throw new RuntimeException();
			}
			
			Iterator<JsonNode> iterator = objectMapper.readTree(json).get("TbPublicWifiInfo").get("row").elements();
			
			iterator.forEachRemaining(j -> {
					try {
						list.add( objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_SNAKE_CASE)
								.readerFor(WifiInfo.class)
								.readValue(j.toString())
								);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
			});
			
			beginIndex = endIndex + 1;
			endIndex += 1000;
		}
		
		int count = wifiInfoRepository.saveAll(list);
		
		// model
		request.setAttribute("listTotalCount", count);
		
		// 성공화면 이동.
		String viewPath = "/load-wifi.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
	}
}
