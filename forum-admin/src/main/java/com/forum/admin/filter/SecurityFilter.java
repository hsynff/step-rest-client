package com.forum.admin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String action = request.getParameter("action");
        if (request.getRequestURI().contains("resources")){
            chain.doFilter(req, resp);
            return;
        }
        if ("login".equals(action) || "doLogin".equals(action)) {
            chain.doFilter(req, resp);
            return;
        }
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/ns?action=login");
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
