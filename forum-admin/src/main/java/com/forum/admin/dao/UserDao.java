package com.forum.admin.dao;


import com.forum.admin.model.User;
import com.forum.common.exceptions.UserCredentialsException;

import java.util.List;

public interface UserDao {

    User loginUser(String email, String password) throws UserCredentialsException, UserCredentialsException;

    List<User> getActiveOrBlockedUsers(int status);

    boolean activateUser(int id);

    boolean blockUser(int id);

}
