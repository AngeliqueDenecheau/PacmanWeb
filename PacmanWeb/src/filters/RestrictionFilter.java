package filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

/**
 * Servlet Filter implementation class Filter
 */
@WebFilter(filterName = "RestrictionFilter", urlPatterns = "/WEB-INF/restricted/*", dispatcherTypes = {DispatcherType.FORWARD})
public class RestrictionFilter implements Filter {
	public static final String ATTR_USER = "user";
	public static final String VUE_CONNECTION = "/connection";

    /**
     * Default constructor. 
     */
    public RestrictionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see RestrictionFilter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	/**
	 * @see RestrictionFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute(ATTR_USER);
		
		if(user == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + VUE_CONNECTION);
		}else {			
			chain.doFilter(httpRequest, httpResponse);
		}
	}

	/**
	 * @see RestrictionFilter#destroy()
	 */
	public void destroy() {
		
	}
}
