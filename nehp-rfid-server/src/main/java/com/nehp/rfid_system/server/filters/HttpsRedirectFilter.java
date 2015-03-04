package com.nehp.rfid_system.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects all non-SSL encrypted requests to the SSL encrypted URL.
 * 
 */
public class HttpsRedirectFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		StringBuffer uri = ((HttpServletRequest) request).getRequestURL();
		if (uri.toString().startsWith("http://")
				&& !uri.toString().endsWith("ping")) {
			String location = "https://" + uri.substring("http://".length());
			((HttpServletResponse) response).sendRedirect(location);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}

}
