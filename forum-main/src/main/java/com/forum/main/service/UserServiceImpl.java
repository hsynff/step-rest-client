package com.forum.main.service;


import com.forum.common.exceptions.ForumException;
import com.forum.common.exceptions.UserCredentialsException;
import com.forum.main.dao.UserDao;
import com.forum.main.model.Action;
import com.forum.main.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email, String password) throws UserCredentialsException, ForumException {
        return userDao.loginUser(email, password);
    }

    @Override
    public void registerUser(User user) throws ForumException {
        userDao.registerUser(user);
    }

    @Override
    public List<Action> getActionListByRoleId(int idRole) throws ForumException {
        return userDao.getActionListByRoleId(idRole);
    }
}
