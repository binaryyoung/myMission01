package mission.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextListenerImpl implements ServletContextListener{

	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
//		System.out.println("서버 시작");
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
//		System.out.println("서버 종료");
	}
}