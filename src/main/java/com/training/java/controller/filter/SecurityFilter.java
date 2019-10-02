package com.training.java.controller.filter;


import com.training.java.model.entity.UserAccount;
import com.training.java.request.UserRoleRequestWrapper;
import com.training.java.controller.utils.AppUtils;
import com.training.java.controller.utils.SecurityUtils;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class SecurityFilter implements Filter {

    public SecurityFilter() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String servletPath = request.getServletPath();
        UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());

        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;

        if (loginedUser != null) {
            String userName = loginedUser.getUserName();
            List<String> roles = loginedUser.getRoles();
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }

        if (SecurityUtils.isSecurityPage(request)) {
            if (loginedUser == null) {

                String requestUri = request.getRequestURI();
                AppUtils.storeRedirectAfterLoginUrl(requestUri);
                response.sendRedirect(wrapRequest.getContextPath() + "/login");
                return;
            }
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");

                dispatcher.forward(request, response);
                return;
            }
        }
        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

}