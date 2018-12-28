package com.hr.global.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CodingFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String jQueryHeader=req.getHeader("X-Requested-With");
	    if (jQueryHeader!= null && jQueryHeader.equalsIgnoreCase("XMLHttpRequest")) {
	        request.setCharacterEncoding("UTF-8");
	    } else {
	        request.setCharacterEncoding("GBK");
	    }
	    filterChain.doFilter(request, response);

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
