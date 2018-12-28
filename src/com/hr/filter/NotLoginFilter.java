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
		if(null==user){//�û�δ��¼
			String contextPath=request.getContextPath();
			String suffix=null;
			if(!"".equals(contextPath)){//��Ŀ������rootĿ¼����Ĭ��Ŀ¼ʱcontextPathΪ�մ�
				accessFile=accessFile.replaceFirst(contextPath, "");
			}
			int index=accessFile.lastIndexOf(".");
			if(index!=-1){
				suffix=accessFile.substring(index);
			}
			if("/".equals(accessFile)||"/index.jsp".equals(accessFile)||"/superAdmin.jsp".equals(accessFile)
					||"/do/UserDo/login".equals(accessFile)||"/do/BankNodeDo/getAllBankNode".equals(accessFile)){//���ʵ�¼ҳ��Ҫ����
				filterChain.doFilter(servletRequest, servletResponse);
			}else if(null==suffix||".jsp".equalsIgnoreCase(suffix)){//����servlet(servlet�����޺�׺)��������jspҳ����Ҫ����
				HttpServletResponse response=(HttpServletResponse) servletResponse;
				response.sendRedirect(contextPath+"/index.jsp");
			}else{//����������Դ(�磺ͼƬ��css�ļ���)��Ҫ����
				filterChain.doFilter(servletRequest, servletResponse);
				//filterChain.doFilter(new OpRequestWrap((HttpServletRequest) servletRequest), servletResponse);
			}
		}else{//�û��Ѿ���¼
			filterChain.doFilter(servletRequest, servletResponse);
			//filterChain.doFilter(new OpRequestWrap((HttpServletRequest) servletRequest), servletResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
