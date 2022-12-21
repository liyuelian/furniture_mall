package com.li.furns.web;

import com.li.furns.entity.Furn;
import com.li.furns.entity.Page;
import com.li.furns.service.FurnService;
import com.li.furns.service.impl.FurnServiceImpl;
import com.li.furns.utils.DataUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CustomerFurnServlet extends BasicServlet {

    private FurnService furnService = new FurnServiceImpl();

    /**
     * 处理首页分页请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这里的业务逻辑和原先的家居后台分页非常相似
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //调用service方法，获取Page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        //将page放入request域中
        req.setAttribute("page", page);
        //请求转发到/views/customer/index.jsp - 真正的主页
        req.getRequestDispatcher("/views/customer/index.jsp")
                .forward(req, resp);
    }


    /**
     * 处理首页搜索请求
     * 并进行分页（只对搜索到的信息分页）
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //如果参数有name但没有值，接收的就是空串
        //如果参数没有name，接收到的就是null
        String furnName = req.getParameter("furnName");
        //这样做，将""和null的业务逻辑合并在一起
        if (furnName == null) {
            furnName = "";
        }
        //调用service方法，获取Page对象
        Page<Furn> page = furnService.pageByName(pageNo, pageSize, furnName);
        //根据furnName
        StringBuilder url = new StringBuilder("customerFurnServlet?action=pageByName");
        if (!"".equals(furnName)) {//如果furnName不为空串，就拼接 furnName参数
            url.append("&furnName=").append(furnName);
        }
        page.setUrl(url.toString());
        //将page放入request中
        req.setAttribute("page", page);
        //请求转发到/customer/index.jsp
        req.getRequestDispatcher("/views/customer/index.jsp")
                .forward(req, resp);
    }
}
