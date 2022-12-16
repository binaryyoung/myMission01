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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import mission.domain.wifiinfo.WifiInfo;
import mission.domain.wifiinfo.WifiInfoRepository;
import mission.util.DistanceCalculator;
import mission.util.DistanceCalculator.DistanceUnit;

@WebServlet("/wifiinfo")
public class WifiInfoListServlet extends HttpServlet{
	
	private WifiInfoRepository wifiInfoRepository = WifiInfoRepository.getInstance();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Double lat = Double.parseDouble(request.getParameter("lat"));
		Double lnt = Double.parseDouble(request.getParameter("lnt"));
		
		// TODO 예외 처리?
		if (lat == null || lnt == null) {
			return;
		}
		
		List<WifiInfo> wifiInfoList = wifiInfoRepository.findByLatAndLnt(lat, lnt);
		
		// 모델 추가
		request.setAttribute("wifiInfoList", wifiInfoList);
		
		// 뷰 생성
		String viewPath = "/index.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
	}
	
	
	
	
}
