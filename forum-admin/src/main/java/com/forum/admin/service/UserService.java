package com.forum.admin.service;


import com.forum.admin.model.User;
import com.forum.common.exceptions.UserCredentialsException;

import java.util.List;

public interface UserService {

    User loginUser(String email, String password) throws UserCredentialsException;

    List<User> getActiveOrBlockedUsers(int status);

    boolean activateUser(int id);

    boolean blockUser(int id);

}
