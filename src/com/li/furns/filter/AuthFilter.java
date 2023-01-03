package com.li.furns.filter;

import com.google.gson.Gson;
import com.li.furns.entity.Member;
import com.li.furns.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 这是用于权限验证的过滤器，对指定的url进行验证
 * 如果登录过，就放行；如果没有登录，就返回登录页面
 *
 * @author 李
 * @version 1.0
 */
public class AuthFilter implements Filter {
    //后面我们把要排除的url放入到excludedUrls中
    private List<String> excludedUrls;

    public void init(FilterConfig config) throws ServletException {
        //获取到配置的excludedUrls
        String strExcludedUrls = config.getInitParameter("excludedUrls");
        //进行分割
        String[] splitUrl = strExcludedUrls.split(",");
        //将splitUrl转成List,赋给excludedUrls
        excludedUrls = Arrays.asList(splitUrl);
        System.out.println("excludedUrls=>" + excludedUrls);
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //权限验证
        HttpServletRequest req = (HttpServletRequest) request;
        //得到请求的url
        String url = req.getServletPath();

        //判断是否要验证
        if (!excludedUrls.contains(url)) {//如果url不在配置的规则中，就进行校验
            //得到session中的member对象
            Member member = (Member) req.getSession().getAttribute("member");
            if (member == null) {//说明用户没有登录过
                //先判断该请求是否为Ajax请求
                if (!WebUtils.isAjaxRequest(req)) {//不是ajax请求
                    //转发到登录页面
                    //不要使用重定向，因为重定向的url符合过滤器规则时也会被拦截，
                    //如果设置不合理就会出现 请求无线循环重定向的 情况
                    req.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
                } else {//如果是ajax请求
                    //以json格式返回一个url
                    HashMap<String, Object> resultMap = new HashMap<>();
                    resultMap.put("url", "views/member/login.jsp");
                    String resultJson = new Gson().toJson(resultMap);
                    response.getWriter().write(resultJson);
                }
                return;//返回
            } else {//否则说明用户已经登录过了
                if (!member.getUsername().equals("admin") && url.contains("/manage/")) {
                    //如果该用户不是admin但请求的url属于后台管理，就将其转发到首页
                    req.getRequestDispatcher("/index.jsp").forward(request, response);
                    return;//返回
                }
            }
        }
        //否则就放行
        chain.doFilter(request, response);
    }
}
