package com.forum.main.service;

import com.forum.common.exceptions.ForumException;
import com.forum.common.exceptions.UserCredentialsException;
import com.forum.main.model.Action;
import com.forum.main.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User loginUser(String email, String password)  throws UserCredentialsException, ForumException;
    void registerUser(User user) throws ForumException;
    List<Action> getActionListByRoleId(int idRole) throws ForumException;
}
