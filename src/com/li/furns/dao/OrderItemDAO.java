package com.li.furns.dao;

import com.li.furns.entity.OrderItem;

/**
 * OrderItemDAO表示一个订单项
 *
 * @author 李
 * @version 1.0
 */
public interface OrderItemDAO {
    /**
     * 将传入的orderItem对象保存到order_item表中
     *
     * @param orderItem
     * @return
     */
    public int saveOrderItem(OrderItem orderItem);

}
