package com.forum.admin.servlet;


import com.forum.admin.dao.jdbc.UserDaoImpl;
import com.forum.admin.model.User;
import com.forum.admin.service.UserService;
import com.forum.admin.service.UserServiceImpl;
import com.forum.common.constants.NavigationConstants;
import com.forum.common.constants.UserConstants;
import com.forum.common.exceptions.UserCredentialsException;
import com.forum.common.util.CryptoUtil;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/us")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl(new UserDaoImpl());
    private UserService userServiceJpa = new UserServiceImpl(new com.forum.admin.dao.jpa.UserDaoImpl());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = null;

        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        } else {
            response.sendRedirect("/");
            return;
        }

        if (action.equals(NavigationConstants.ACTION_GET_ALL_ACTIVE_USERS)) {
            List<User> activeUsers = userServiceJpa.getActiveOrBlockedUsers(UserConstants.USER_STATUS_ACTIVE);
            JSONArray jsonArray = new JSONArray(activeUsers);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals(NavigationConstants.ACTION_GET_ALL_BLOCKED_USERS)) {
            List<User> blockedUsers = userServiceJpa.getActiveOrBlockedUsers(UserConstants.USER_STATUS_BLOCKED);
            JSONArray jsonArray = new JSONArray(blockedUsers);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals(NavigationConstants.ACTION_BLOCK_USER_BY_ID)) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            boolean result = userServiceJpa.blockUser(idUser);

        } else if (action.equals(NavigationConstants.ACTION_ACTIVATE_USER_BY_ID)) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            boolean result = userServiceJpa.activateUser(idUser);

        } else if (action.equals(NavigationConstants.ACTION_DO_LOGIN)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            try {
                User user = userServiceJpa.loginUser(email, CryptoUtil.inputToHash(password));
                request.getSession().setAttribute("userForum", user);
                response.sendRedirect("/");
            } catch (UserCredentialsException e) {
                request.setAttribute("message", e.getMessage());
                request.getRequestDispatcher(NavigationConstants.PAGE_LOGIN).forward(request, response);
            }
        } else if (action.equals(NavigationConstants.ACTION_LOGOUT)) {
            HttpSession session = request.getSession();
            session.removeAttribute("userForum");
            session.invalidate();
            response.sendRedirect("/ns?action=login");
        }


    }
}