package com.hit.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web-app Listener
 * @author Daniel Tal
 * @author Hadas Barel
 */
public class AdminListener implements HttpSessionListener
{

	/**
	 * Default constructor
	 */
	public AdminListener()
	{}

	/**
	 * @see HttpSession#sessionCreated(HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent hse)
	{

		HttpSession session = hse.getSession();
		ServletContext application = session.getServletContext();
		if (application.getAttribute("sessions") == null)
		{
			application.setAttribute("sessions", new ArrayList<HttpSession>());
		}
		@SuppressWarnings("unchecked")
		List<HttpSession> lst = (List<HttpSession>)application.getAttribute("sessions");
		lst.add(session);
		application.setAttribute("sessions", lst);
	}

	/**
	 * @see HttpSession#sessionDestroyed(HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent hse)
	{
		HttpSession session = hse.getSession();
		ServletContext application = session.getServletContext();
		List<HttpSession> lst = (List<HttpSession>)application.getAttribute("sessions");
		lst.remove(session);
		application.setAttribute("sessions", lst);
	}

}
