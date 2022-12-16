package com.li.furns.web;

import com.li.furns.entity.Member;
import com.li.furns.service.MemberService;
import com.li.furns.service.impl.MemberServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 该Servlet处理和Member相关的请求
 *
 * @author 李
 * @version 1.0
 */
public class MemberServlet extends BasicServlet {
    private MemberService memberService = new MemberServiceImpl();

    /**
     * 处理会员登录业务
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * 处理会员注册业务
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收用户注册信息--参数名要以前端页面的变量名为准
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //如果返回false，说明该用户信息可以注册
        if (!memberService.isExistsUsername(username)) {
            //构建一个member对象
            Member member = new Member(null, username, password, email);
            if (memberService.registerMember(member)) {
                //如果注册成功，请求转发到register_ok.html
                request.getRequestDispatcher("/views/member/register_ok.html")
                        .forward(request, response);
            } else {
                //注册失败，请求转发到register_fail.html
                request.getRequestDispatcher("/views/member/register_fail.html")
                        .forward(request, response);
            }
        } else {//否则不能进行注册
            //请求转发到login.html
            //后面可以加入提示信息
            request.getRequestDispatcher("/views/member/login.jsp")
                    .forward(request, response);
        }
    }
}
