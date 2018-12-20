package com.forum.admin.dao.jdbc;

import com.forum.admin.dao.UserDao;
import com.forum.admin.model.Role;
import com.forum.admin.model.User;
import com.forum.common.constants.MessageConstants;
import com.forum.common.constants.UserConstants;
import com.forum.common.exceptions.UserCredentialsException;
import com.forum.common.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String GET_ACTIVE_OR_BLOCKED_USERS = "select id_user, email, status, id_role, first_name, last_name from user where status = ?";
    private static final String ACTIVATE_USER_BY_ID_SQL = "update user set status = ? where id_user = ?";
    private static final String BLOCK_USER_BY_ID_SQL = "update user set status = ? where id_user = ?";
    private final String GET_USER_BY_EMAIL_SQL = "select * from user where email = ? and id_role = ?";


    @Override
    public User loginUser(String email, String password) throws UserCredentialsException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_USER_BY_EMAIL_SQL);
            ps.setString(1, email);
            ps.setInt(2, UserConstants.ROLE_ID_ADMIN);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (!password.equals(rs.getString("password"))) {
                    throw new UserCredentialsException(MessageConstants.ERROR_INVALID_PASSWORD);
                }
                if (rs.getInt("status") != UserConstants.USER_STATUS_ACTIVE) {
                    throw new UserCredentialsException(MessageConstants.ERROR_INACTIVE_ACCOUNT);
                }
                user = new User();
                user.setId(rs.getInt("id_user"));
                user.setFirstname(rs.getString("first_name"));
                user.setLastname(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setImagePath(rs.getString("img"));
                Role role = new Role();
                role.setId(rs.getInt("id_role"));
                user.setRole(role);

            } else {
                throw new UserCredentialsException(MessageConstants.ERROR_INVALID_EMAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserCredentialsException(MessageConstants.ERROR_INTERNAL);
        } finally {
            DbUtil.closeAll(con, ps, rs);
        }
        return user;
    }

    @Override
    public List<User> getActiveOrBlockedUsers(int status) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(GET_ACTIVE_OR_BLOCKED_USERS);
            ps.setInt(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id_user"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("first_name"));
                user.setLastname(rs.getString("last_name"));
                user.setStatus(rs.getInt("status"));

                Role role = new Role();
                role.setId(rs.getInt("id_role"));
                user.setRole(role);

                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps, rs);
        }

        return list;
    }

    @Override
    public boolean activateUser(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(BLOCK_USER_BY_ID_SQL);
            ps.setInt(1, UserConstants.USER_STATUS_ACTIVE);
            ps.setInt(2, id);
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
    public boolean blockUser(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean result = false;

        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(BLOCK_USER_BY_ID_SQL);
            ps.setInt(1, UserConstants.USER_STATUS_INACTIVE);
            ps.setInt(2, id);
            ps.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DbUtil.closeAll(con, ps);
        }

        return result;
    }







}