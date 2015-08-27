package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter{

	private ServletContext context;
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.context = config.getServletContext();
	}


	@Override
	public void doFilter(ServletRequest requset, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) requset;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(false);

		if(session == null){
			resp.sendRedirect("login");			
		}else{
			chain.doFilter(requset, response);
		}
	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
