package com.forum.admin.servlet;

import com.forum.common.constants.NavigationConstants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NavigationServlet", urlPatterns = "/ns")
public class NavigationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = null;
        String address = null;

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        } else {
            response.sendRedirect("/");
            return;
        }

        if (action.equals(NavigationConstants.ACTION_PENDING_TOPICS)) {
            address = NavigationConstants.PAGE_PENDING_TOPICS;

        } else if (action.equals(NavigationConstants.ACTION_ACTIVE_USERS)) {
            address = NavigationConstants.PAGE_ACTIVE_USERS;

        } else if (action.equals(NavigationConstants.ACTION_BLOCKED_USERS)) {
            address = NavigationConstants.PAGE_BLOCKED_USERS;

        } else if (action.equals(NavigationConstants.ACTION_LOGIN)) {
            address = NavigationConstants.PAGE_LOGIN;

        } else if (action.equals(NavigationConstants.ACTION_INBOX)) {
            address = NavigationConstants.PAGE_INBOX;

        } else if (action.equals(NavigationConstants.ACTION_MAIL_COMPOSE)) {
            address = NavigationConstants.PAGE_MAIL_COMPOSE;

        } else if (action.equals(NavigationConstants.ACTION_MAIL_VIEW)) {
            address = NavigationConstants.PAGE_MAIL_VIEW;

        }

        if (address != null) {
            request.getRequestDispatcher(address).forward(request, response);
        }
    }
}
