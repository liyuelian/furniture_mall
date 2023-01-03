package com.li.furns.web;

import com.li.furns.entity.Admin;
import com.li.furns.service.AdminService;
import com.li.furns.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 李
 * @version 1.0
 */
public class AdminServlet extends BasicServlet {
    private AdminService adminService = new AdminServiceImpl();

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户输入的账号密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //构建一个Admin对象
        Admin admin = new Admin(null, username, password, null);
        if (adminService.login(admin) == null) {
            //登录失败,返回管理员登录页面并提示错误信息
            request.setAttribute("errInfo", "登录失败，账号信息有误");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/views/manage/manage_login.jsp")
                    .forward(request, response);
        } else {
            //登录成功，跳转到manage_menu.jsp
            request.getRequestDispatcher("/views/manage/manage_menu.jsp")
                    .forward(request, response);
        }
    }
}
