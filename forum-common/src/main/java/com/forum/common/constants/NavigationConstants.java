package com.forum.common.constants;

public class NavigationConstants {

    //prefixes
    private static final String PAGE_PREFIX_VIEW = "/WEB-INF/view/";
    private static final String PAGE_PREFIX_FRAGMENT = "/WEB-INF/fragment/";

    // forum-main actions
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_DO_REGISTER = "doRegister";
    public static final String ACTION_GET_TOPIC_BY_USER_ID = "getTopicByUserId";
    public static final String ACTION_DO_LOGIN = "doLogin";
    public static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_ADD_COMMENT = "addComment";
    public static final String ACTION_GET_POPULAR_TOPICS = "getPopularTopics";
    public static final String ACTION_ADD_NEW_TOPIC = "addNewTopic";
    public static final String ACTION_GET_SIMILAR_TOPICS = "getSimilarTopics";
    public static final String ACTION_GET_COMMENTS_BY_TOPIC_ID = "getCommentsByTopicId";
    public static final String ACTION_TOPIC = "topic";
    public static final String ACTION_NEW_TOPIC ="newTopic";
    public static final String ACTION_NEW_ACCOUNT="newAccount";


    //forum-main pages
    public static final String PAGE_INDEX = PAGE_PREFIX_VIEW + "index.jsp";
    public static final String PAGE_NEW_ACCOUNT = PAGE_PREFIX_VIEW + "new-account.jsp";
    public static final String PAGE_TOPIC = PAGE_PREFIX_VIEW + "topic.jsp";
    public static final String PAGE_NEW_TOPIC = PAGE_PREFIX_VIEW + "new-topic.jsp";

    public static final String PAGE_SIMILAR_TOPICS_FRAGMENTS = PAGE_PREFIX_FRAGMENT + "similar-topics-fragments.jsp";
    public static final String PAGE_COMMENTS = PAGE_PREFIX_FRAGMENT + "comments.jsp";

    //forum-admin actions
    public static final String ACTION_PENDING_TOPICS = "pending-topics";
    public static final String ACTION_ACTIVE_USERS = "active-users";
    public static final String ACTION_BLOCKED_USERS = "blocked-users";
    public static final String ACTION_INBOX = "inbox";
    public static final String ACTION_MAIL_COMPOSE = "mail-compose";
    public static final String ACTION_MAIL_VIEW = "mail-view";
    public static final String ACTION_GET_ALL_ACTIVE_USERS = "getAllActiveUsers";
    public static final String ACTION_GET_ALL_BLOCKED_USERS = "getAllBlockedUsers";
    public static final String ACTION_BLOCK_USER_BY_ID = "blockUserById";
    public static final String ACTION_ACTIVATE_USER_BY_ID = "activateUserById";


    //forum-admin pages
    public static final String PAGE_INBOX = PAGE_PREFIX_VIEW + "inbox.jsp";
    public static final String PAGE_ACTIVE_TOPICS = PAGE_PREFIX_VIEW + "active-topics.jsp";
    public static final String PAGE_LOGIN = PAGE_PREFIX_VIEW + "login.jsp";
    public static final String PAGE_MAIL_COMPOSE = PAGE_PREFIX_VIEW + "mail_compose.jsp";
    public static final String PAGE_MAIL_VIEW = PAGE_PREFIX_VIEW + "mail_view.jsp";
    public static final String PAGE_PENDING_TOPICS = PAGE_PREFIX_VIEW + "pending-topics.jsp";
    public static final String PAGE_ACTIVE_USERS = PAGE_PREFIX_VIEW + "active-users.jsp";
    public static final String PAGE_BLOCKED_USERS = PAGE_PREFIX_VIEW + "blocked-users.jsp";

    public static final String FRAGMENT_FOOTER = PAGE_PREFIX_FRAGMENT + "page-footer.jsp";
    public static final String FRAGMENT_HEADER = PAGE_PREFIX_FRAGMENT + "page-header.jsp";
    public static final String FRAGMENT_SIDEBAR = PAGE_PREFIX_FRAGMENT + "page-sidebar.jsp";
    public static final String FRAGMENT_TOPIC = PAGE_PREFIX_FRAGMENT + "topic.jsp";


}
