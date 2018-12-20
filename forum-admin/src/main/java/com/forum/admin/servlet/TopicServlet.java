package com.forum.admin.servlet;



import com.forum.admin.dao.jdbc.TopicDaoImpl;
import com.forum.admin.model.Topic;
import com.forum.admin.service.TopicService;
import com.forum.admin.service.TopicServiceImpl;
import com.forum.common.constants.NavigationConstants;
import com.forum.common.constants.TopicConstants;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TopicServlet", urlPatterns = "/ts")
public class TopicServlet extends HttpServlet {

    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());
    private TopicService topicServiceJpa = new TopicServiceImpl(new com.forum.admin.dao.jpa.TopicDaoImpl());


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

        if (action.equals("getAllActiveTopics")) {
            List<Topic> activeTopics = topicServiceJpa.getActiveOrPendingTopics(TopicConstants.TOPIC_STATUS_ACTIVE);

            JSONArray jsonArray = new JSONArray(activeTopics);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals("getAllPendingTopics")) {
            List<Topic> pendingTopics = topicServiceJpa.getActiveOrPendingTopics(TopicConstants.TOPIC_STATUS_INACTIVE);
            JSONArray jsonArray = new JSONArray(pendingTopics);
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals("deleteTopicById")) {
            int idTopic = Integer.parseInt(request.getParameter("id"));
            boolean result = topicServiceJpa.deleteTopic(idTopic);

        } else if (action.equals("activateTopicById")) {
            int idTopic = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String desc = request.getParameter("desc");
            boolean result = topicServiceJpa.activateTopic(idTopic, title, desc);
            if (!result){
                throw  new ServletException();
            }

        } else if (action.equals("getTopicById")){
            int topicId = Integer.parseInt(request.getParameter("id"));
            Topic topic = topicServiceJpa.getTopicById(topicId);

            if (topic == null){
                throw new ServletException();
            }
            request.setAttribute("topic", topic);
            request.getRequestDispatcher(NavigationConstants.FRAGMENT_TOPIC).forward(request, response);
        }
    }
}
