package com.li.furns.web;

import com.li.furns.entity.Cart;
import com.li.furns.entity.Member;
import com.li.furns.entity.Order;
import com.li.furns.entity.OrderItem;
import com.li.furns.service.OrderService;
import com.li.furns.service.impl.OrderServiceImpl;
import com.li.furns.utils.DataUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 显示订单order
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取当前用户id
        Member member = (Member) req.getSession().getAttribute("member");
        if (member == null) {
            //重定向到登录页面
            resp.sendRedirect(req.getContextPath() + "/views/member/login.jsp");
            return;
        }
        int id = DataUtils.parseInt(member.getId().toString(), 0);
        //根据用户id查询对应的订单
        List<Order> orders = orderService.queryOrderByMemberId(id);
        //将订单数据返回显示
        req.setAttribute("orders", orders);
        //请求转发到order.jsp显示
        req.getRequestDispatcher("/views/order/order.jsp").forward(req, resp);
    }


    /**
     * 显示订单项orderItem
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showOrderItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取当前orderid
        String orderId = req.getParameter("orderId");
        //根据orderId返回对应的订单项orderItem
        List<OrderItem> orderItems = orderService.queryOrderItemByOrderId(orderId);
        //计算该order中所有orderItem的总商品数量和总价格
        Integer totalCount = 0;
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (OrderItem orderItem : orderItems) {
            totalCount += orderItem.getCount();
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
        }
        System.out.println("totalCount=>" + totalCount);
        System.out.println("totalPrice=>" + totalPrice);
        //将订单项数据返回显示
        //订单id
        req.setAttribute("orderId", orderId);
        //订单商品总数
        req.setAttribute("totalCount", totalCount);
        //订单商品总额
        req.setAttribute("totalPrice", totalPrice);
        //订单项
        req.setAttribute("orderItems", orderItems);
        //请求转发到order_detail.jsp
        req.getRequestDispatcher("/views/order/order_detail.jsp").forward(req, resp);
    }
}
