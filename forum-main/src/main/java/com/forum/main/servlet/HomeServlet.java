package com.forum.main.servlet;

import com.forum.common.constants.NavigationConstants;
import com.forum.common.exceptions.ForumException;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "")
public class HomeServlet extends HttpServlet {

//    private TopicService topicServiceJpa = new TopicServiceImpl(new com.forum.main.dao.jpa.TopicDaoImpl());
    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Topic> list = new ArrayList<>();
        try {
            list = topicService.getAllTopic();

        } catch (ForumException e) {
            e.printStackTrace();
        }

        request.setAttribute("topicList", list);
        if (request.getSession().getAttribute("message")!=null) {
            request.setAttribute("message", request.getSession().getAttribute("message"));
            request.getSession().removeAttribute("message");

        }

        request.getRequestDispatcher(NavigationConstants.PAGE_INDEX).forward(request,response);

    }

}
