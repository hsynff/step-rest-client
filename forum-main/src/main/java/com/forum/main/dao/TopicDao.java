package com.forum.main.dao;


import com.forum.common.exceptions.ForumException;
import com.forum.main.model.Comment;
import com.forum.main.model.Topic;

import java.sql.SQLException;
import java.util.List;

public interface TopicDao {
    List<Topic> getAllTopic() throws ForumException;
    Topic getTopicById(int id) throws ForumException;
    List<Topic> getPopularTopics() throws ForumException;
    void addTopic(Topic topic) throws ForumException;
    void updateTopicViewCount(int topicId) throws ForumException;
    List<Topic> getSimilarTopics(String[] keywords) throws ForumException;
    List<Comment> getCommentsByTopicId(int id) throws ForumException;
    void addComment(Comment comment) throws ForumException;
    List<Topic> getTopicByUserId(int idUser) throws ForumException;
}
