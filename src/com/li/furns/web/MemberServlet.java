package com.li.furns.web;

import com.li.furns.entity.Member;
import com.li.furns.service.MemberService;
import com.li.furns.service.impl.MemberServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

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

        //2.调用MemberServiceImpl的login方法
        Member member = memberService.login(new Member(null, username, password, null));
        if (member == null) {//数据库中没有该用户，返回登录页面
            //登录失败，将错误信息和登录会员名放入request域中
            request.setAttribute("errInfo", "登录失败，用户名或者密码错误");
            request.setAttribute("username", username);
            //注意路径
            request.getRequestDispatcher("/views/member/login.jsp")
                    .forward(request, response);
        } else {//登录成功
            //创建session，将jsessionid作为cookie返回给浏览器
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1800);//设置生命周期为30分钟
            //将得到的member对象放入session域对象中
            session.setAttribute("member", member);
            //跳转到登录成功页面
            request.getRequestDispatcher("/views/member/login_ok.jsp")
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
        //获取用户提交的验证码
        String code = request.getParameter("code");
        //从session中获取正确的验证码文本
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //立即删除session的验证码，防止该验证码被重复使用
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //将提交的验证码和正确的验证码文本进行对比
        //如果token不为空，并且和用户提交的验证码一致，就继续执行业务
        if (token != null && token.equalsIgnoreCase(code)) {
            //如果返回false，说明该用户信息可以注册
            if (!memberService.isExistsUsername(username)) {
                //构建一个member对象
                Member member = new Member(null, username, password, email);
                if (memberService.registerMember(member)) {
                    //如果注册成功，请求转发到register_ok.html
                    request.getRequestDispatcher("/views/member/register_ok.jsp")
                            .forward(request, response);
                } else {
                    //注册失败，请求转发到register_fail.html
                    request.getRequestDispatcher("/views/member/register_fail.jsp")
                            .forward(request, response);
                }
            } else {//否则不能进行注册
                //请求转发到login.jsp
                //后面可以加入提示信息
                request.getRequestDispatcher("/views/member/login.jsp")
                        .forward(request, response);
            }
        } else {//验证码不正确
            request.setAttribute("errInfo", "验证码不正确");
            //回显注册信息
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            //带回一个信息，显示到注册选项页
            request.setAttribute("active", "register");
            request.getRequestDispatcher("/views/member/login.jsp")
                    .forward(request, response);
        }
    }

    /**
     * 处理用户注销登录的请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //销毁当前用户的session
        req.getSession().invalidate();
        //重定向到index.jsp，目的是刷新首页
        //req.getContextPath()=>/项目名  -默认访问index.jsp
        resp.sendRedirect(req.getContextPath());
    }
}
