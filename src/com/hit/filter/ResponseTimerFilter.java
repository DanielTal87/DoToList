package com.hit.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class ResponseTimerFilter
 */
public class ResponseTimerFilter implements Filter
{
	protected FilterConfig config;

	/**
	 * Default constructor.
	 */
	public ResponseTimerFilter()
	{}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		long startTime = System.currentTimeMillis();
		chain.doFilter(request, response);
		long totalTime = System.currentTimeMillis() - startTime;
		String name = "servlet";
		if (request instanceof HttpServletRequest)
		{
			name = ((HttpServletRequest)request).getRequestURI();
		}

		config.getServletContext().log(name + " took " + totalTime + " msec");
	}

	/**
	 * 
	 * @param fConfig
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		this.config = fConfig;
	}

}

// test
