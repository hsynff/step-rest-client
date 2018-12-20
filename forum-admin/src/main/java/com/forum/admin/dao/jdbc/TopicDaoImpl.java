package com.forum.admin.dao.jdbc;

import com.forum.admin.dao.TopicDao;
import com.forum.admin.model.Topic;
import com.forum.admin.model.User;
import com.forum.common.constants.TopicConstants;
import com.forum.common.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoImpl implements TopicDao {

    private final String GET_ACTIVE_OR_PENDING_TOPICS_SQL = "select t.id_topic, t.title, t.description as topic_description, t.share_date, t.view_count, t.status, u.id_user, u.email, u.first_name, u.last_name from topic t inner join user u on t.id_user = u.id_user where t.status = ?";
    private final static String DELETE_tOPIC_BY_ID_SQL = "delete from topic where id_topic = ?";
    private final static String ACTIVATE_TOPIC_BY_ID_SQL = "update topic set status = ?, title =?, description = ? where id_topic = ?";
    private final String GET_TOPIC_BY_ID_SQL = "select t.id_topic, t.title, t.description as t_description, t.share_date, t.view_count, t.status, u.id_user as t_id_user, u.first_name as t_first_name, u.img as t_img, u.last_name as t_last_name  from topic t inner join user u on t.id_user=u.id_user where t.id_topic=?";


    @Override
    public List<Topic> getActiveOrPendingTopics(int status) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Topic> list = new ArrayList<>();

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_ACTIVE_OR_PENDING_TOPICS_SQL);
            ps.setInt(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id_topic"));
                topic.setTitle(rs.getString("title"));
                topic.setDescription(rs.getString("topic_description"));
                topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                topic.setViewCount(rs.getInt("view_count"));
                topic.setStatus(rs.getInt("status"));

                User user = new User();
                user.setId(rs.getInt("id_user"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("first_name"));
                user.setLastname(rs.getString("last_name"));

                topic.setUser(user);
                list.add(topic);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return list;
    }

    @Override
    public boolean deleteTopic(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(DELETE_tOPIC_BY_ID_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps);
        }

        return result;
    }

    @Override
    public boolean activateTopic(int id, String title, String desc) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(ACTIVATE_TOPIC_BY_ID_SQL);
            ps.setInt(1, TopicConstants.TOPIC_STATUS_ACTIVE);
            ps.setString(2, title);
            ps.setString(3, desc);
            ps.setInt(4, id);
            ps.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps);
        }

        return result;
    }

    @Override
    public Topic getTopicById(int id)  {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Topic topic = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_TOPIC_BY_ID_SQL);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                topic = new Topic();
                topic.setId(rs.getInt("id_topic"));
                topic.setTitle(rs.getString("title"));
                topic.setDescription(rs.getString("t_description"));
                topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                topic.setViewCount(rs.getInt("view_count"));

                User user = new User();
                user.setId(rs.getInt("t_id_user"));
                user.setFirstname(rs.getString("t_first_name"));
                user.setLastname(rs.getString("t_last_name"));
                user.setImagePath(rs.getString("t_img"));
                topic.setUser(user);

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtil.closeAll(con, ps, rs);
        }
        System.out.println(topic);
        return topic;
    }
}
