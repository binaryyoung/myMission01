package mission.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mission.domain.history.History;
import mission.domain.history.HistoryRepository;
import mission.domain.history.impl.HistoryJavaBeanRepository;
import mission.domain.history.impl.HistoryMariaDbRepository;
import mission.domain.wifiinfo.WifiInfo;
import mission.domain.wifiinfo.WifiInfoRepository;
import mission.domain.wifiinfo.impl.WifiInfoJavaBeanRepository;
import mission.domain.wifiinfo.impl.WifiInfoMariaDbRepository;

@WebServlet("/wifiinfo")
public class WifiInfoListServlet extends HttpServlet{
	
	private WifiInfoRepository wifiInfoRepository = WifiInfoMariaDbRepository.getInstance();
	private HistoryRepository historyRepository = HistoryMariaDbRepository.getInstance();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Double lat = Double.parseDouble(request.getParameter("lat"));
		Double lnt = Double.parseDouble(request.getParameter("lnt"));
		
		List<WifiInfo> wifiInfoList = wifiInfoRepository.findByLatAndLnt(lat, lnt);

		// 위치 히스토리 추가
		History history = new History(lat, lnt, LocalDateTime.now()); 
		historyRepository.save(history);
		
		// 모델 추가
		request.setAttribute("wifiInfoList", wifiInfoList);
		
		// 뷰 생성
		String viewPath = "/index.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
	}
}
