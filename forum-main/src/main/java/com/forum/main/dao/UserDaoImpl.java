package com.forum.main.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.forum.common.constants.MessageConstants;
import com.forum.common.constants.UserConstants;
import com.forum.common.exceptions.ForumException;
import com.forum.common.exceptions.UserCredentialsException;
import com.forum.common.util.DbUtil;
import com.forum.main.dao.UserDao;
import com.forum.main.model.Action;
import com.forum.main.model.Role;
import com.forum.main.model.User;
import com.forum.main.model.response.ResponseModel;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends ForumWebServiceManager implements UserDao {


    @Override
    public User loginUser(String email, String password) throws UserCredentialsException, ForumException {
        User user = null;
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_USERS)
                .path("email")
                .path(email)
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);
        if (responseModel.getStatus().equals(STATUS_SUCCESS)) {

            user = objectToType(responseModel, new TypeReference<User>() {
            });

            if (!user.getPassword().equals(password)) {
                throw new UserCredentialsException(MessageConstants.ERROR_INVALID_PASSWORD);
            }
            if (user.getStatus() != UserConstants.USER_STATUS_ACTIVE) {
                throw new UserCredentialsException(MessageConstants.ERROR_INACTIVE_ACCOUNT);
            }

        } else {
            handleError(responseModel);
        }
        return user;
    }

    @Override
    public void registerUser(User user) throws ForumException {
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_USERS)
                .request(JSON)
                .post(Entity.entity(user, JSON))
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_ERROR)){
            handleError(responseModel);
        }
    }

    @Override
    public List<Action> getActionListByRoleId(int idRole) throws ForumException {
        List<Action> list = new ArrayList<>();
        ResponseModel responseModel = ClientBuilder.newClient()
                .target(RESOURCE_ROLES)
                .path(String.valueOf(idRole))
                .path("actions")
                .request(JSON)
                .get()
                .readEntity(ResponseModel.class);

        if (responseModel.getStatus().equals(STATUS_SUCCESS)){
            list = objectToType(responseModel, new TypeReference<List<Action>>(){});
        }else {
            handleError(responseModel);
        }

        return list;
    }
}
