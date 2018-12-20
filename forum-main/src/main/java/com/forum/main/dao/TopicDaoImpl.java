package com.forum.main.dao;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.forum.common.constants.TopicConstants;
import com.forum.common.exceptions.ForumException;
import com.forum.common.util.Config;
import com.forum.common.util.DbUtil;
import com.forum.main.dao.TopicDao;
import com.forum.main.model.Comment;
import com.forum.main.model.Topic;
import com.forum.main.model.User;
import com.forum.main.model.response.ErrorWrapper;
import com.forum.main.model.response.ResponseModel;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TopicDaoImpl extends ForumWebServiceManager implements TopicDao {


    @Override
    public List<Topic> getAllTopic() throws ForumException {
        List<Topic> list = new ArrayList<>();
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
           list = objectToType(responseModel, new TypeReference<List<Topic>>(){});
        }else{
            handleError(responseModel);
        }
        return list;
    }

    @Override
    public Topic getTopicById(int id) throws ForumException {
        Topic topic = null;
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .path(String.valueOf(id))
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
           topic = objectToType(responseModel, new TypeReference<Topic>(){});
        }else{
            handleError(responseModel);
        }

        return topic;
    }

    @Override
    public List<Topic> getPopularTopics() throws ForumException {
        List<Topic> list = new ArrayList<>();
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .path("popular")
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);
        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
            list = objectToType(responseModel, new TypeReference<List<Topic>>(){});
        }else {
           handleError(responseModel);
        }

        return list;
    }

    @Override
    public void addTopic(Topic topic) throws ForumException {
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .request(JSON)
                .post(Entity.entity(topic, JSON))
                .readEntity(ResponseModel.class);
        if (responseModel.getStatus().equals(STATUS_ERROR)){
            handleError(responseModel);
        }
    }

    @Override
    public void updateTopicViewCount(int topicId) throws ForumException {
        Response response = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .path(String.valueOf(topicId))
                .path("viewCount")
                .request(JSON)
                .put(Entity.entity("{}", JSON));

        if (response.getStatus() != 204) {
            ResponseModel responseModel = response.readEntity(ResponseModel.class);
            handleError(responseModel);
        }
    }

    @Override
    public List<Topic> getSimilarTopics(String[] keywords) throws ForumException {
        List<Topic> list = new ArrayList<>();
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .queryParam("keywords", Arrays.asList(keywords))
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
            list = objectToType(responseModel, new TypeReference<List<Topic>>(){});
        }else{
            handleError(responseModel);
        }

        return list;
    }

    @Override
    public List<Comment> getCommentsByTopicId(int id) throws ForumException {
        List<Comment> list = new ArrayList<>();
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_TOPICS)
                .path(String.valueOf(id))
                .path("comments")
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
            list = objectToType(responseModel, new TypeReference<List<Comment>>(){});
        }else{
            handleError(responseModel);
        }

        return list;
    }

    @Override
    public void addComment(Comment comment) throws ForumException {
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_COMMENTS)
                .request(JSON)
                .post(Entity.entity(comment, JSON))
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_ERROR)){
            handleError(responseModel);
        }
    }

    @Override
    public List<Topic> getTopicByUserId(int idUser) throws ForumException {
        List<Topic> list = new ArrayList<>();
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_USERS)
                .path(String.valueOf(idUser))
                .path("topics")
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
            list = objectToType(responseModel, new TypeReference<List<Topic>>(){});
        }else{
            handleError(responseModel);
        }

        return list;
    }

}
