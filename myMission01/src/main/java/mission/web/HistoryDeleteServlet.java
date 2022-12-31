package mission.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mission.domain.history.HistoryRepository;
import mission.domain.history.impl.HistoryJavaBeanRepository;
import mission.domain.history.impl.HistoryMariaDbRepository;

@WebServlet("/history/delete")
public class HistoryDeleteServlet extends HttpServlet{

	private HistoryRepository historyRepository = HistoryMariaDbRepository.getInstance();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Long id = Long.parseLong(request.getParameter("id"));
		
		historyRepository.delete(id);
		
		String servletPath = "/history";
        RequestDispatcher dispatcher = request.getRequestDispatcher(servletPath);
        dispatcher.forward(request, response);
	}
}
