package com.forum.main.servlet;

import com.forum.common.constants.NavigationConstants;
import com.forum.main.dao.TopicDaoImpl;
import com.forum.main.model.Topic;
import com.forum.main.service.TopicService;
import com.forum.main.service.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "NavigationServlet", urlPatterns = "/ns")
public class NavigationServlet extends HttpServlet {

//    private TopicService topicServiceJpa = new TopicServiceImpl(new com.forum.main.dao.jpa.TopicDaoImpl());
    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(response, request);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(response, request);
    }

    private void processRequest(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        String action = null;
        String address = null;


        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        } else {
            response.sendRedirect("/");
            return;
        }


        if (action.equals(NavigationConstants.ACTION_TOPIC)) {
            String idT = request.getParameter("id");
            int idTopic = Integer.parseInt(idT);
            Topic topic = null;
            try {
                topic = topicService.getTopicById(idTopic);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (topic == null){
                response.sendRedirect("/");
                return;
            }
            request.setAttribute("topic", topic);
            try {
                topicService.updateTopicViewCount(idTopic);
            } catch (Exception e) {
                e.printStackTrace();
            }

            address = NavigationConstants.PAGE_TOPIC;

        } else if (action.equals(NavigationConstants.ACTION_NEW_TOPIC)) {
            address = NavigationConstants.PAGE_NEW_TOPIC;
        } else if (action.equals(NavigationConstants.ACTION_NEW_ACCOUNT)) {
            address = NavigationConstants.PAGE_NEW_ACCOUNT;
        } else if (action.equals(NavigationConstants.ACTION_LOGIN)) {
            address = NavigationConstants.PAGE_LOGIN;
        }


        if (address != null) {
            request.getRequestDispatcher(address).forward(request, response);
        }


    }
}
