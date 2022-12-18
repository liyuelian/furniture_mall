//package com.li.furns.web;
//
//import com.li.furns.entity.Member;
//import com.li.furns.service.MemberService;
//import com.li.furns.service.impl.MemberServiceImpl;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import java.io.IOException;
//
//public class RegisterServlet extends HttpServlet {
//
//    //定义一个属性MemberService
//    private MemberService memberService = new MemberServiceImpl();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //接收用户注册信息--参数名要以前端页面的变量名为准
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
//
//        //如果返回false，说明该用户信息可以注册
//        if (!memberService.isExistsUsername(username)) {
//            //构建一个member对象
//            Member member = new Member(null, username, password, email);
//            if (memberService.registerMember(member)) {
//                //如果注册成功，请求转发到register_ok.html
//                request.getRequestDispatcher("/views/member/register_ok.html")
//                        .forward(request, response);
//            } else {
//                //注册失败，请求转发到register_fail.html
//                request.getRequestDispatcher("/views/member/register_fail.html")
//                        .forward(request, response);
//            }
//        } else {//否则不能进行注册
//            //请求转发到login.html
//            //后面可以加入提示信息
//            request.getRequestDispatcher("/views/member/login.jsp")
//                    .forward(request, response);
//        }
//    }
//}
