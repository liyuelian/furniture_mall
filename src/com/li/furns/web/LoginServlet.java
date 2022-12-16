package com.li.furns.web;

import com.li.furns.entity.Member;
import com.li.furns.service.MemberService;
import com.li.furns.service.impl.MemberServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private MemberService memberService = new MemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收用户名和密码
        //如果前端输入的是null，后台接收的数据为空串""
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //构建一个member对象
        Member member = new Member(null, username, password, null);

        //2.调用MemberServiceImpl的login方法
        if (memberService.login(member) == null) {//数据库中没有该用户，返回登录页面
            //登录失败，将错误信息和登录会员名放入request域中
            request.setAttribute("errInfo", "登录失败，用户名或者密码错误");
            request.setAttribute("username", username);
            //注意路径
            request.getRequestDispatcher("/views/member/login.jsp")
                    .forward(request, response);
        } else {
            //否则，跳转到登录成功页面
            request.getRequestDispatcher("/views/member/login_ok.html")
                    .forward(request, response);
        }
    }
}
