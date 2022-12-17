package com.li.furns.test;

import com.li.furns.dao.AdminDAO;
import com.li.furns.dao.impl.AdminDAOImpl;
import com.li.furns.entity.Admin;
import org.junit.jupiter.api.Test;

public class AdminDAOImplTest {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Test
    public void queryMemberByUsernameAndPassword() {
        Admin admin =
                adminDAO.queryAdminByUsernameAndPassword("admin", "admin");
        if (admin == null) {
            System.out.println("没有该用户");
        } else {
            System.out.println("存在该用户");
        }
    }
}
