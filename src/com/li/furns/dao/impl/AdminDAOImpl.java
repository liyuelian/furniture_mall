package com.li.furns.dao.impl;

import com.li.furns.dao.AdminDAO;
import com.li.furns.dao.BasicDAO;
import com.li.furns.entity.Admin;

/**
 * @author 李
 * @version 1.0
 */
public class AdminDAOImpl extends BasicDAO<Admin> implements AdminDAO {
    /**
     * 通过用户名和密码返回对应的Admin对象
     *
     * @param username 管理员用户名
     * @param password 管理员密码
     * @return 如果为对应的Admin对象，如果不存在则返回null
     */
    @Override
    public Admin queryAdminByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM `admin` WHERE `username`=? AND `password`=MD5(?);";
        return querySingle(sql, Admin.class, username, password);
    }
}
