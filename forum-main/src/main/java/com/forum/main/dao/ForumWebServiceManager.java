package com.forum.main.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.forum.common.exceptions.ForumException;
import com.forum.common.util.Config;
import com.forum.main.model.response.ErrorWrapper;
import com.forum.main.model.response.ResponseModel;

import javax.ws.rs.core.MediaType;

abstract class ForumWebServiceManager {
    static final String RESOURCE_TOPICS = Config.getForumServiceHost() + "topics";
    static final String RESOURCE_COMMENTS = Config.getForumServiceHost() + "comments";
    static final String RESOURCE_USERS = Config.getForumServiceHost() + "users";
    static final String RESOURCE_ROLES = Config.getForumServiceHost() + "roles";

    static final String JSON = MediaType.APPLICATION_JSON;
    static final String STATUS_SUCCESS = "success";
    static final String STATUS_ERROR = "error";

    void handleError(ResponseModel responseModel) throws ForumException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ErrorWrapper wrapper = mapper.convertValue(responseModel.getBody(), new TypeReference<ErrorWrapper>(){});
        throw new ForumException(wrapper.getMessage());
    }


    <T> T objectToType(ResponseModel responseModel, TypeReference<T> typeReference){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.convertValue(responseModel.getBody(), typeReference);
    }
}
