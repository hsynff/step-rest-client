package com.forum.main.servlet;

import com.forum.common.constants.MessageConstants;
import com.forum.common.constants.NavigationConstants;
import com.forum.common.constants.UserConstants;
import com.forum.common.exceptions.ForumException;
import com.forum.common.exceptions.UserCredentialsException;
import com.forum.common.util.Config;
import com.forum.common.util.CryptoUtil;
import com.forum.main.dao.UserDaoImpl;
import com.forum.main.model.Role;
import com.forum.main.model.User;
import com.forum.main.service.UserService;
import com.forum.main.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(name = "UserServlet", urlPatterns = "/us")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5)
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl(new UserDaoImpl());

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

        if (action.equals(NavigationConstants.ACTION_DO_LOGIN)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            try {
                User user = userService.loginUser(email, CryptoUtil.inputToHash(password));

                request.getSession().setAttribute("user", user);
                response.sendRedirect("/");

            } catch (UserCredentialsException e) {
                request.setAttribute("message", e.getMessage());
                address = NavigationConstants.PAGE_LOGIN;
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", MessageConstants.ERROR_INTERNAL);
                address = NavigationConstants.PAGE_LOGIN;
            }

        } else if (action.equals(NavigationConstants.ACTION_LOGOUT)) {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            session.invalidate();
            response.sendRedirect("/");
        } else if (action.equals(NavigationConstants.ACTION_DO_REGISTER)) {


            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("pass");


            //TODO validate
            if (!password.equals(request.getParameter("pass2"))) {
                request.setAttribute("message", MessageConstants.ERROR_PASSWORD_IS_NOT_MATCH);
                request.getRequestDispatcher(NavigationConstants.PAGE_NEW_ACCOUNT).forward(request, response);
                return;
            }

            Part img = request.getPart("img");

            Path pathToSaveFile = Paths.get(Config.getImageUploadPath(), email);

            if (!Files.exists(pathToSaveFile)) {
                Files.createDirectories(pathToSaveFile);
            }

            Path file = Paths.get(pathToSaveFile.toString(), img.getSubmittedFileName());

            Files.copy(img.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);

            Path pathToSaveDb = Paths.get(email, img.getSubmittedFileName());

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setImagePath(pathToSaveDb.toString());
            user.setEmail(email);
            user.setPassword(CryptoUtil.inputToHash(password));
            user.setStatus(UserConstants.USER_STATUS_INACTIVE);
            user.setToken(UUID.randomUUID().toString());
            Role role = new Role();
            role.setId(UserConstants.ROLE_ID_USER);
            user.setRole(role);
            try {
                userService.registerUser(user);
                request.getSession().setAttribute("message", MessageConstants.SUCCESS_REGISTER);
                response.sendRedirect("/");
            } catch (ForumException e) {
                request.setAttribute("message", MessageConstants.ERROR_INTERNAL);
                request.getRequestDispatcher(NavigationConstants.PAGE_NEW_ACCOUNT).forward(request, response);
            }

        }

        if (address != null) {
            request.getRequestDispatcher(address).forward(request, response);
        }


    }
}
