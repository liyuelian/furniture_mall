package com.li.furns.service.impl;

import com.li.furns.dao.AdminDAO;
import com.li.furns.dao.impl.AdminDAOImpl;
import com.li.furns.entity.Admin;
import com.li.furns.service.AdminService;

/**
 * @author 李
 * @version 1.0
 */
public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    public Admin login(Admin admin) {
        return adminDAO.queryAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
    }
}
