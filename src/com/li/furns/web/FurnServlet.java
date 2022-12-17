package com.li.furns.web;

import com.li.furns.entity.Furn;
import com.li.furns.service.FurnService;
import com.li.furns.service.impl.FurnServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FurnServlet extends BasicServlet {

    private FurnService furnService = new FurnServiceImpl();

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Furn> furns = furnService.queryFurns();
        //将furns集合放入到request域中
        req.setAttribute("furns", furns);
        //请求转发
        req.getRequestDispatcher("/views/manage/furn_manage.jsp")
                .forward(req, resp);
    }
}
