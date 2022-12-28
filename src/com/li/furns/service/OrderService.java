package com.li.furns.service;

import com.li.furns.entity.Cart;

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
}
