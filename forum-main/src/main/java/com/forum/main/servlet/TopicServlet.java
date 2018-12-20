package com.forum.main.servlet;

import com.forum.common.constants.MessageConstants;
import com.forum.common.constants.NavigationConstants;
import com.forum.common.constants.TopicConstants;
import com.forum.common.exceptions.ForumException;
import com.forum.main.dao.TopicDaoImpl;
import com.forum.main.job.PopularTopicsUpdater;
import com.forum.main.model.Comment;
import com.forum.main.model.Topic;
import com.forum.main.model.User;
import com.forum.main.service.TopicService;
import com.forum.main.service.TopicServiceImpl;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "TopicServlet", urlPatterns = "/ts")
public class TopicServlet extends HttpServlet {

    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());
//    private TopicService topicServiceJpa = new TopicServiceImpl(new com.forum.main.dao.jpa.TopicDaoImpl());
    private PopularTopicsUpdater updater;

    @Override
    public void init() throws ServletException {
        updater = new PopularTopicsUpdater(topicService);
        updater.startJob();
    }

    @Override
    public void destroy() {
        updater.stopJob();
    }

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


        if (action.equals(NavigationConstants.ACTION_GET_POPULAR_TOPICS)) {
            List<Topic> listTopics = updater.getPopularTopics();
            JSONArray jsonArray = new JSONArray(listTopics);
            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());

        } else if (action.equals(NavigationConstants.ACTION_ADD_NEW_TOPIC)) {
            String title = request.getParameter("title");
            String desc = request.getParameter("desc");
            User user = (User) request.getSession().getAttribute("user");

            Topic topic = new Topic();
            topic.setTitle(title);
            topic.setDesc(desc);
            topic.setShareDate(LocalDateTime.now());
            topic.setViewCount(0);
            topic.setUser(user);
            topic.setStatus(TopicConstants.TOPIC_STATUS_INACTIVE);

            try {
                topicService.addTopic(topic);
                request.getSession().setAttribute("message", MessageConstants.SUCCESS_ADD_TOPIC);
            } catch (ForumException e) {
                e.printStackTrace();
                request.getSession().setAttribute("message", MessageConstants.ERROR_INTERNAL);
            }


            response.sendRedirect("/");


        } else if (action.equals(NavigationConstants.ACTION_GET_SIMILAR_TOPICS)) {
            String title = request.getParameter("title");
            String[] keywords = title.trim().split(" ");
            keywords = Arrays.stream(keywords).filter(s -> s.length() >= 3).toArray(s -> new String[s]);
            System.out.println(Arrays.toString(keywords));
            List<Topic> topicList = new ArrayList<>();
            try {
                topicList = keywords.length == 0 ? new ArrayList<>() : topicService.getSimilarTopics(keywords);
            } catch (ForumException e) {
                e.printStackTrace();
            }
            request.setAttribute("topicList", topicList);
            address = NavigationConstants.PAGE_SIMILAR_TOPICS_FRAGMENTS;


        } else if (action.equals(NavigationConstants.ACTION_GET_COMMENTS_BY_TOPIC_ID)) {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Comment> listComments = new ArrayList<>();

            try {
                listComments = topicService.getCommentsByTopicId(id);
            } catch (ForumException e) {
                e.printStackTrace();
            }

            request.setAttribute("listComments", listComments);
            address = NavigationConstants.PAGE_COMMENTS;

        } else if (action.equals(NavigationConstants.ACTION_ADD_COMMENT)) {
            String idTopic = request.getParameter("idTopic");
            String desc = request.getParameter("desc");
            Topic topic = new Topic();
            topic.setId(Integer.parseInt(idTopic));
            User user = (User) request.getSession().getAttribute("user");
            Comment comment = new Comment();
            comment.setDesc(desc);
            comment.setTopic(topic);
            comment.setUser(user);
            comment.setWriteDate(LocalDateTime.now());
            try {
                topicService.addComment(comment);
            } catch (ForumException e) {
                e.printStackTrace();
                throw new ServletException();
            }


        } else if (action.equals(NavigationConstants.ACTION_GET_TOPIC_BY_USER_ID)) {
            User user = (User) request.getSession().getAttribute("user");
            List<Topic> topicList = new ArrayList<>();
            try {
                topicList = topicService.getTopicByUserId(user.getId());
            } catch (ForumException e) {
                e.printStackTrace();
            }

            topicList = topicList.stream().map(t ->
            {
                t.setCommentList(null);
                return t;
            }).collect(Collectors.toList());

            JSONArray jsonArray = new JSONArray(topicList);
            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());

        }


        if (address != null) {
            request.getRequestDispatcher(address).forward(request, response);
        }


    }
}


