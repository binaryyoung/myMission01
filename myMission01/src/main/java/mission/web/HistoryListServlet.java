package mission.web;

import java.io.IOException;
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

@WebServlet("/history")
public class HistoryListServlet extends HttpServlet{
	
	
	private HistoryRepository historyRepository = HistoryMariaDbRepository.getInstance();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		List<History> historyList = historyRepository.findAll(); 
		
		// 모델 추가
		request.setAttribute("historyList", historyList);
		
		// 뷰 생성
		String viewPath = "/history.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
	}
}
