package com.li.furns.test;

import com.li.furns.entity.Admin;
import com.li.furns.service.AdminService;
import com.li.furns.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;

public class AdminServiceImplTest {
    private AdminService adminService = new AdminServiceImpl();

    @Test
    public void login() {
        Admin admin =
                adminService.login(new Admin(null, "admin", "admin", null));
        System.out.println("admin=" + admin);
    }
}
