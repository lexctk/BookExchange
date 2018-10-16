package com.bookexchange.servlets.authentication;

import java.io.IOException;
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

@WebFilter("/app/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest filterRequest = (HttpServletRequest) request;
        HttpServletResponse filterResponse = (HttpServletResponse) response;
        
        HttpSession session = filterRequest.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
        	filterResponse.sendRedirect(filterRequest.getContextPath() + "/login"); // No logged-in user found, so redirect to login page.
        } else {
            chain.doFilter(request, response); // Logged-in user found, so just continue request.
        }
    }

    @Override
    public void destroy() {
    }

}