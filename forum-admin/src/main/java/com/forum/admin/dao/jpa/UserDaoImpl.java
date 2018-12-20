package com.forum.admin.dao.jpa;

import com.forum.admin.dao.UserDao;
import com.forum.admin.model.User;
import com.forum.admin.util.HibernateUtil;
import com.forum.common.constants.MessageConstants;
import com.forum.common.constants.UserConstants;
import com.forum.common.exceptions.UserCredentialsException;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.net.UnknownServiceException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User loginUser(String email, String password) throws UserCredentialsException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user;
        try {

            user = session.createQuery("from User u where u.email= :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();

            chechUserCredentials(user, password);

            Hibernate.initialize(user.getRole());

        }finally {
            session.getTransaction().commit();
        }




        return user;
    }

    @Override
    public List<User> getActiveOrBlockedUsers(int status) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        List<User> list = session.createQuery("from User u join fetch u.role where u.status= :status", User.class)
                .setParameter("status", status)
                .list();

        session.getTransaction().commit();
        return list;
    }

    @Override
    public boolean activateUser(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.createQuery("update User u set u.status = :status where u.id= :id")
                .setParameter("status", UserConstants.USER_STATUS_ACTIVE)
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean blockUser(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.createQuery("update User u set u.status = :status where u.id= :id")
                .setParameter("status", UserConstants.USER_STATUS_BLOCKED)
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();
        return true;
    }

    private void chechUserCredentials(User user, String enteredPassword) throws UserCredentialsException {
        String errorMsg = null;

        if (user == null) {
            errorMsg = MessageConstants.ERROR_INVALID_EMAIL;
        } else if (!user.getPassword().equals(enteredPassword)) {
            errorMsg = MessageConstants.ERROR_INVALID_PASSWORD;
        } else if (user.getStatus()!= UserConstants.USER_STATUS_ACTIVE){
            errorMsg = MessageConstants.ERROR_INACTIVE_ACCOUNT;
        }

        if (errorMsg!=null){
            throw new UserCredentialsException(errorMsg);
        }
    }
}
