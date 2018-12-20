package com.forum.admin.dao.jpa;

import com.forum.admin.dao.TopicDao;
import com.forum.admin.model.Topic;
import com.forum.admin.util.HibernateUtil;
import com.forum.common.constants.TopicConstants;
import org.hibernate.Session;

import java.util.List;

public class TopicDaoImpl implements TopicDao {


    @Override
    public List<Topic> getActiveOrPendingTopics(int status) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        List<Topic> list = session.createQuery("from Topic t where t.status = :status", Topic.class)
                .setParameter("status", status)
                .list();

        session.getTransaction().commit();
        return list;
    }

    @Override
    public boolean deleteTopic(int id) {
        Topic topic = new Topic();
        topic.setId(id);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.delete(topic);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean activateTopic(int id, String title, String desc) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.createQuery("update Topic t set t.title = :title, t.description = :desc, t.status = :status where t.id= :id")
                .setParameter("title", title)
                .setParameter("desc", desc)
                .setParameter("status", TopicConstants.TOPIC_STATUS_ACTIVE)
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();
        return true;
    }

    @Override
    public Topic getTopicById(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Topic topic = session.get(Topic.class, id);

        session.getTransaction().commit();
        return topic;

    }
}
