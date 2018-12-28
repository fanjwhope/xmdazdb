package com.hr.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.bean.User;
import com.hr.global.util.ArchiveUtil;

public class NotLoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		HttpServletRequest request=(HttpServletRequest) servletRequest;
		User user=ArchiveUtil.getCurrentUser(request.getSession());
		String accessFile=request.getRequestURI();
		if(null==user){//用户未登录
			String contextPath=request.getContextPath();
			String suffix=null;
			if(!"".equals(contextPath)){//项目部署在root目录或者默认目录时contextPath为空串
				accessFile=accessFile.replaceFirst(contextPath, "");
			}
			int index=accessFile.lastIndexOf(".");
			if(index!=-1){
				suffix=accessFile.substring(index);
			}
			if("/".equals(accessFile)||"/index.jsp".equals(accessFile)||"/superAdmin.jsp".equals(accessFile)
					||"/do/UserDo/login".equals(accessFile)||"/do/BankNodeDo/getAllBankNode".equals(accessFile)){//访问登录页需要放行
				filterChain.doFilter(servletRequest, servletResponse);
			}else if(null==suffix||".jsp".equalsIgnoreCase(suffix)){//访问servlet(servlet访问无后缀)或者其他jsp页面需要拦截
				HttpServletResponse response=(HttpServletResponse) servletResponse;
				response.sendRedirect(contextPath+"/index.jsp");
			}else{//访问其他资源(如：图片。css文件等)需要放行
				filterChain.doFilter(servletRequest, servletResponse);
				//filterChain.doFilter(new OpRequestWrap((HttpServletRequest) servletRequest), servletResponse);
			}
		}else{//用户已经登录
			filterChain.doFilter(servletRequest, servletResponse);
			//filterChain.doFilter(new OpRequestWrap((HttpServletRequest) servletRequest), servletResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
