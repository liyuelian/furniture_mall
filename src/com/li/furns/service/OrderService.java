package com.li.furns.service;

import com.li.furns.entity.Cart;
import com.li.furns.entity.Order;
import com.li.furns.entity.OrderItem;

import java.util.List;

/**
 * @author 李
 * @version 1.0
 */
public interface OrderService {
    /**
     * 1.生成订单
     * 2.订单是根据购物车来生成的，cart对象在session，通过web层，传入saveOrder
     * 3.订单和会员id关联
     *
     * @param cart     购物车
     * @param memberId 会员id
     * @return 返回生成的订单号
     */
    public String saveOrder(Cart cart, int memberId);

    /**
     * 根据用户id返回订单order
     *
     * @param id 用户id
     * @return 订单
     */
    public List<Order> queryOrderByMemberId(int id);

    /**
     * 根据订单id返回对应的订单项orderItem
     *
     * @param orderId 订单id
     * @return 返回订单项
     */
    public List<OrderItem> queryOrderItemByOrderId(String orderId);
}
