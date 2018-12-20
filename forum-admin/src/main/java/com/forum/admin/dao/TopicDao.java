package com.forum.admin.dao;

import com.forum.admin.model.Topic;

import java.util.List;

public interface TopicDao {

    List<Topic> getActiveOrPendingTopics(int status);

    boolean deleteTopic(int id);

    boolean activateTopic(int id, String title, String desc);

    Topic getTopicById(int id);

}
