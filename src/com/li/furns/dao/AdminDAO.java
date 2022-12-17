package com.li.furns.dao;

import com.li.furns.entity.Admin;

/**
 * @author 李
 * @version 1.0
 */
public interface AdminDAO {
    //提供一个通过用户名和密码返回对应的Admin的方法
    public Admin queryAdminByUsernameAndPassword(String username, String password);
}
