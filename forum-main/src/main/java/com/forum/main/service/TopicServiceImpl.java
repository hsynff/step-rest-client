package com.forum.main.service;

import com.forum.common.exceptions.ForumException;
import com.forum.main.dao.TopicDao;
import com.forum.main.model.Comment;
import com.forum.main.model.Topic;

import java.sql.SQLException;
import java.util.List;

public class TopicServiceImpl implements TopicService {

    private TopicDao topicDao;

    public TopicServiceImpl(TopicDao topicDao){
        this.topicDao = topicDao;
    }


    @Override
    public List<Topic> getAllTopic() throws ForumException {
        return topicDao.getAllTopic();
    }

    @Override
    public Topic getTopicById(int id)throws ForumException {
        return topicDao.getTopicById(id);
    }

    @Override
    public List<Topic> getPopularTopics()throws ForumException {
        return topicDao.getPopularTopics();
    }

    @Override
    public void addTopic(Topic topic)throws ForumException {
        topicDao.addTopic(topic);
    }

    @Override
    public void updateTopicViewCount(int topicId)throws ForumException {
        topicDao.updateTopicViewCount(topicId);
    }

    @Override
    public List<Topic> getSimilarTopics(String[] keywords)throws ForumException {
        return topicDao.getSimilarTopics(keywords);
    }

    @Override
    public List<Comment> getCommentsByTopicId(int id)throws ForumException {
        return topicDao.getCommentsByTopicId(id);
    }

    @Override
    public void addComment(Comment comment)throws ForumException {
        topicDao.addComment(comment);
    }

    @Override
    public List<Topic> getTopicByUserId(int idUser)throws ForumException {
        return topicDao.getTopicByUserId(idUser);
    }
}
