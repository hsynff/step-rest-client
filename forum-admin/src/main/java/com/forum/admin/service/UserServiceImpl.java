package com.forum.admin.service;

import com.forum.admin.dao.UserDao;
import com.forum.admin.model.User;
import com.forum.common.exceptions.UserCredentialsException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String password) throws UserCredentialsException {
        return userDao.loginUser(email, password);
    }

    @Override
    public List<User> getActiveOrBlockedUsers(int status) {
        return userDao.getActiveOrBlockedUsers(status);
    }

    @Override
    public boolean activateUser(int id) {
        return userDao.activateUser(id);
    }

    @Override
    public boolean blockUser(int id) {
        return userDao.blockUser(id);
    }

}
