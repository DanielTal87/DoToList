package com.hit.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web-app life-cycle Listener
 */
public class AdminListener implements HttpSessionListener
{
	/**
	 * sessions
	 */
	private List<HttpSession> sessions;

	/**
	 * Default constructor
	 */
	public AdminListener()
	{
		sessions = new ArrayList<>();
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent hse)
	{
		sessions.add(hse.getSession());
		hse.getSession().setAttribute("sessionList", sessions);
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent hse)
	{
		for (HttpSession session : sessions)
		{
			if (hse.getSession() == session)
			{
				sessions.remove(session);
			}
		}
		hse.getSession().setAttribute("sessionList", sessions);
	}

}
