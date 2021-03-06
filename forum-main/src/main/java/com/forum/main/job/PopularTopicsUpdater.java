package com.forum.main.job;


import com.forum.main.model.Topic;
import com.forum.main.service.TopicService;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PopularTopicsUpdater {
    private List<Topic> popularTopics;
    private ScheduledExecutorService service;
    private TopicService topicService;

    public PopularTopicsUpdater(TopicService topicService) {
        this.topicService = topicService;
    }


    private void updatePopularTopics() {
        service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(
                () -> {
                    try {
                        popularTopics = topicService.getPopularTopics();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                0, 20, TimeUnit.SECONDS);

    }

    public void startJob() {
        updatePopularTopics();
    }

    public void stopJob() {
        if (service != null) {
            service.shutdown();
        }
    }


    public List<Topic> getPopularTopics() {
        return popularTopics;
    }
}
