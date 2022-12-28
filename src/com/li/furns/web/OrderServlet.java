package com.li.furns.web;

import com.li.furns.entity.Cart;
import com.li.furns.entity.Member;
import com.li.furns.service.OrderService;
import com.li.furns.service.impl.OrderServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author 李
 * @version 1.0
 */
public class OrderServlet extends BasicServlet {
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取购物车cart
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (null == cart || cart.isEmpty()) {//购物车不存在session或者购物车没有任何东西
            // 转发到首页
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;//结束业务
        }
        //获取当前登录的memberId
        Member member = (Member) req.getSession().getAttribute("member");
        if (null == member) {//如果没有登录
            //转发到登录页面
            req.getRequestDispatcher("/views/member/login.jsp").forward(req, resp);
            return;//结束业务
        }

        //生成订单
        String orderId = orderService.saveOrder(cart, member.getId());
        req.getSession().setAttribute("orderId", orderId);

        //使用重定向到checkout.jsp页面
        resp.sendRedirect(req.getContextPath() + "/views/order/checkout.jsp");

    }
}
