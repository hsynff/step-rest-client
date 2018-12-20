package com.forum.admin.util;

import com.forum.admin.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory factory;

    static {
        factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Action.class)
                .addAnnotatedClass(Comment.class)
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(Topic.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public static Session getSession(){
       return factory.getCurrentSession();
    }
}
