package mission.web;

import java.io.IOException;
import java.util.Iterator;

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
import mission.infra.ApiExplorer;


@WebServlet(urlPatterns = "/init")
public class WifiInfoAddServlet extends HttpServlet{
	
	private WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
	
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
		
		// System.out.println("listTotalCount=" + listTotalCount);
		
		while (beginIndex < listTotalCount) {
			// System.out.println("beginIndex=" + beginIndex);
			// System.out.println("endIndex=" + endIndex);
			
			json = apiExplorer.requestApi(beginIndex, endIndex);
			
			String code = objectMapper.readTree(json).get("TbPublicWifiInfo").get("RESULT").get("CODE").asText();
			if (!code.equals("INFO-000")) {
				break;
			}
			
			Iterator<JsonNode> iterator = objectMapper.readTree(json).get("TbPublicWifiInfo").get("row").elements();
			iterator.forEachRemaining(j -> {
					try {
						wifiInfoRepository.save( objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_SNAKE_CASE)
								.readerFor(WifiInfo.class)
								.readValue(j.toString())
								);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
			});
			
			beginIndex += 1000;
			endIndex += 1000;
		}
		
		// model
		request.setAttribute("listTotalCount", listTotalCount);
		
		// 성공화면 이동.
		String viewPath = "/load-wifi.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
	}
}
