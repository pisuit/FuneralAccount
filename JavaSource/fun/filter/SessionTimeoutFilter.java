package fun.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import fun.utils.Constants;

public class SessionTimeoutFilter implements Filter {

	private String loginpage = "/pages/login.jsf";
	
	public String getLoginpage() {
		return loginpage;
	}

	public void setLoginpage(String loginpage) {
		this.loginpage = loginpage;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("begin session filter");
		if ((request instanceof HttpServletRequest)
				&& (response instanceof HttpServletResponse)) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

//			if (isSessionControlRequiredForThisResource(httpServletRequest)) {

				// is session invalid?
				if (isSessionInvalid(httpServletRequest)) {
					String timeoutUrl = httpServletRequest.getContextPath()
							+ "/" + getLoginpage();
					httpServletResponse.sendRedirect(timeoutUrl);
					return;
				}
//			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private boolean isSessionControlRequiredForThisResource(
			HttpServletRequest httpServletRequest) {
		String requestPath = httpServletRequest.getRequestURI();

		boolean controlRequired = !StringUtils.contains(requestPath,
				getLoginpage());

		return controlRequired;
	}

	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
				&& !httpServletRequest.isRequestedSessionIdValid();
		return sessionInValid;
	}

}
